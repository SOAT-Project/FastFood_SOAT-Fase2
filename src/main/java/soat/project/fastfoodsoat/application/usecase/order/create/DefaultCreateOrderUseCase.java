package soat.project.fastfoodsoat.application.usecase.order.create;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientGateway;
import soat.project.fastfoodsoat.domain.client.ClientPublicId;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentGateway;
import soat.project.fastfoodsoat.domain.payment.PaymentService;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Transactional
@Component
public class DefaultCreateOrderUseCase extends CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;
    private final ClientGateway clientGateway;
    private final PaymentGateway paymentGateway;
    private final PaymentService paymentService;

    public DefaultCreateOrderUseCase(final OrderGateway orderGateway,
                                     final ProductGateway productGateway,
                                     final ClientGateway clientGateway,
                                     final PaymentGateway paymentGateway,
                                     final PaymentService paymentService) {
      
        this.orderGateway = requireNonNull(orderGateway);
        this.productGateway = requireNonNull(productGateway);
        this.clientGateway = clientGateway;
        this.paymentGateway = paymentGateway;
        this.paymentService = paymentService;
    }

    @Override
    public CreateOrderOutput execute(final CreateOrderCommand command) {
        final Notification notification = Notification.create();

        final ClientPublicId clientPublicId = command.clientPublicId() != null ?
                ClientPublicId.of(command.clientPublicId()) : null;

        final var client = clientPublicId != null ?
            clientGateway.findByPublicId(clientPublicId)
                .orElseThrow(() -> NotFoundException.with(Client.class, clientPublicId))
            : null;

        final var clientId = client != null ? client.getId() : null;

        final List<CreateOrderProductCommand> orderProducts = command.orderProducts();

        if (orderProducts.isEmpty()) {
            throw new NotificationException("Order must have at least one product", notification);
        }

        final List<Product> products = productGateway.findByIds(
                orderProducts.stream()
                        .map(CreateOrderProductCommand::productId)
                        .toList()
        );

        final List<OrderProduct> orderProductDomains =
                orderProducts.stream()
                        .map(orderProduct -> {
                            final Product product = products.stream()
                                    .filter(p -> p.getId().getValue().equals(orderProduct.productId()))
                                    .findFirst()
                                    .orElseThrow(() ->  NotFoundException.with(Product.class, new ProductId(orderProduct.productId())));

                            final BigDecimal orderProductValue = product.getValue()
                                    .multiply(BigDecimal.valueOf(orderProduct.quantity()));

                            return OrderProduct.newOrderProduct(
                                    orderProductValue,
                                    orderProduct.quantity(),
                                    product
                            );
                        })
                        .toList();

        final BigDecimal value = Order.calculateValue(orderProductDomains);
        final Integer orderNumber = orderGateway.findLastOrderNumber() + 1;
        final UUID publicId = UUID.randomUUID();
        final UUID externalReference = UUID.randomUUID();

        final Order order = notification.validate(() ->
                Order.newOrder(
                        OrderPublicId.of(publicId),
                        orderNumber,
                        OrderStatus.RECEIVED,
                        clientId,
                        value,
                        orderProductDomains,
                        null
                )
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create order", notification);
        }

        final Order createdOrder = orderGateway.create(order);

        final String qrCodeText = paymentService.createDynamicQrCode(
                orderNumber,
                externalReference,
                value,
                createdOrder.getOrderProducts()
        );

        if (qrCodeText == null) {
            throw new NotificationException("could not create qr code", notification);
        }

        final Payment payment = notification.validate(() ->
                Payment.newPayment(
                        value,
                        externalReference.toString(),
                        qrCodeText,
                        PaymentStatus.PENDING,
                        createdOrder
                )
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create payment", notification);
        }

        final Payment createdPayment = paymentGateway.create(payment);

        return CreateOrderOutput.from(
                createdOrder,
                createdPayment
        );
    }
}
