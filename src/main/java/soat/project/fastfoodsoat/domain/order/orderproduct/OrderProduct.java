package soat.project.fastfoodsoat.domain.order.orderproduct;

import soat.project.fastfoodsoat.domain.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderProduct extends AggregateRoot<OrderProductId> {

    private BigDecimal value;
    private Integer quantity;

    private OrderProduct(
            final OrderProductId orderProductId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt,
            final BigDecimal value,
            final Integer quantity
    ) {
        super(
                orderProductId,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.value = value;
        this.quantity = quantity;
        this.selfValidation();
    }

    public static OrderProduct newOrderProduct(
            final BigDecimal value,
            final Integer quantity
    ) {
        final OrderProductId orderProductId = null;
        final Instant now = Instant.now();
        return new OrderProduct(
                orderProductId,
                now,
                now,
                null,
                value,
                quantity
        );
    }

    public static OrderProduct with(
            final OrderProductId orderProductId,
            final BigDecimal value,
            final Integer quantity,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new OrderProduct(
                orderProductId,
                createdAt,
                updatedAt,
                deletedAt,
                value,
                quantity
        );
    }

    public static OrderProduct from(
            final OrderProduct orderProduct
    ) {
        return new OrderProduct(
                orderProduct.id,
                orderProduct.createdAt,
                orderProduct.updatedAt,
                orderProduct.deletedAt,
                orderProduct.value,
                orderProduct.quantity
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
}
