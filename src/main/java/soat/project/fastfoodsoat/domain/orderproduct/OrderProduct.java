package soat.project.fastfoodsoat.domain.orderproduct;

import soat.project.fastfoodsoat.domain.shared.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderProduct extends AggregateRoot<OrderProductId> {

    private BigDecimal value;
    private Integer quantity;
    private Product product;


    private OrderProduct(
            final OrderProductId orderProductId,
            final BigDecimal value,
            final Integer quantity,
            final Product product,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(
                orderProductId,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.value = value;
        this.quantity = quantity;
        this.product = product;
        this.selfValidation();
    }

    public static OrderProduct newOrderProduct(
            final BigDecimal value,
            final Integer quantity,
            final Product product
            ) {
        final OrderProductId orderProductId = null;
        final Instant now = Instant.now();
        return new OrderProduct(
                orderProductId,
                value,
                quantity,
                product,
                now,
                now,
                null
        );
    }

    public static OrderProduct with(
            final OrderProductId orderProductId,
            final BigDecimal value,
            final Integer quantity,
            final Product product,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new OrderProduct(
                orderProductId,
                value,
                quantity,
                product,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static OrderProduct from(
            final OrderProduct orderProduct
    ) {
        return new OrderProduct(
                orderProduct.id,
                orderProduct.value,
                orderProduct.quantity,
                orderProduct.product,
                orderProduct.createdAt,
                orderProduct.updatedAt,
                orderProduct.deletedAt
        );
    }

    public OrderProduct update(
            final BigDecimal value,
            final Integer quantity
    ) {
        this.value = value;
        this.quantity = quantity;
        this.selfValidation();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new OrderProductValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create order product", notification);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
