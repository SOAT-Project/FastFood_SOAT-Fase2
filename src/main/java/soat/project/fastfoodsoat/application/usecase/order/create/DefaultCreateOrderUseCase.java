package soat.project.fastfoodsoat.application.usecase.order.create;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import static java.util.Objects.requireNonNull;

@Transactional
@Component
public class DefaultCreateOrderUseCase extends CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;

    public DefaultCreateOrderUseCase(final OrderGateway orderGateway,
                                     final ProductGateway productGateway) {
        this.orderGateway = requireNonNull(orderGateway);
        this.productGateway = requireNonNull(productGateway);
    }

    @Override
    public CreateOrderOutput execute(final CreateOrderCommand command) {
        System.out.println("CreateOrderCommand: " + command);
        final var notification = Notification.create();

        final var clientId = command.clientId();
        final var orderProducts = command.orderProducts();
        final var orderNumber = orderGateway.findLastOrderNumber() + 1;
        final var products = productGateway.findByIds(
                orderProducts.stream()
                        .map(CreateOrderProductCommand::productId)
                        .toList()
        );

        final var orderProductDomains = notification.validate(() ->
                orderProducts.stream()
                        .map(orderProduct -> {
                            final var product = products.stream()
                                    .filter(p -> p.getId().getValue().equals(orderProduct.productId()))
                                    .findFirst()
                                    .orElseThrow(() -> NotFoundException.with(Product.class, new ProductId(orderProduct.productId())));

                            return OrderProduct.newOrderProduct(
                                    product.getValue(),
                                    orderProduct.quantity(),
                                    product
                            );
                        })
                        .toList()
        );

        final var order = notification.validate(() ->
                Order.newOrder(
                        orderNumber,
                        OrderStatus.RECEIVED,
                        orderProductDomains
                )
        );

        if (notification.hasError()) {
            throw new NotificationException("could not create product", notification);
        }

        return CreateOrderOutput.from(this.orderGateway.create(order));
    }
}
