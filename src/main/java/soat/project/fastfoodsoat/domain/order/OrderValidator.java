package soat.project.fastfoodsoat.domain.order;

import soat.project.fastfoodsoat.domain.client.ClientId;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderValidator extends Validator {

    private final Order order;

    public OrderValidator(final Order order, final ValidationHandler handler) {
        super(handler);
        this.order = order;
    }

    @Override
    public void validate() {
        checkValueConstraints();
        checkOrderNumberConstraints();
        checkOrderStatusConstraints();
    }


    private void checkValueConstraints() {
        final BigDecimal value = this.order.getValue();
        if (Objects.isNull(value)) {
            this.validationHandler().append(new DomainError("'value' should not be null"));
            return;
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            this.validationHandler().append(new DomainError("'value' should be greater than zero"));
        }
    }

    private void checkOrderNumberConstraints() {
        final Integer orderNumber = this.order.getOrderNumber();
        if (Objects.isNull(orderNumber)) {
            this.validationHandler().append(new DomainError("'orderNumber' should not be null"));
            return;
        }

        if (orderNumber <= 0) {
            this.validationHandler().append(new DomainError("'orderNumber' should be greater than zero"));
        }
    }

    private void checkOrderStatusConstraints() {
        final OrderStatus status = this.order.getStatus();
        if (Objects.isNull(status)) {
            this.validationHandler().append(new DomainError("'status' should not be null"));
        }
    }
}
