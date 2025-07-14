package soat.project.fastfoodsoat.domain.orderproduct;

import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderProductValidator extends Validator {

    private final OrderProduct orderProduct;

    public OrderProductValidator(final OrderProduct orderProduct, final ValidationHandler handler) {
        super(handler);
        this.orderProduct = orderProduct;
    }

    @Override
    public void validate() {
        checkValueConstraints();
        checkQuantityConstraints();
        checkProductConstraints();
    }

    private void checkProductConstraints() {
        final Product product = this.orderProduct.getProduct();
        if (Objects.isNull(product)) {
            this.validationHandler().append(new DomainError("'product' should not be null"));
        }
    }

    private void checkValueConstraints() {
        final BigDecimal value = this.orderProduct.getValue();
        if ((Objects.isNull(value))) {
            this.validationHandler().append(new DomainError("'value' should not be null"));
            return;
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            this.validationHandler().append(new DomainError("'value' should be greater than zero"));
        }
    }

    private void checkQuantityConstraints() {
        final Integer quantity = this.orderProduct.getQuantity();
        if ((Objects.isNull(quantity))) {
            this.validationHandler().append(new DomainError("'quantity' should not be null"));
            return;
        }

        if (quantity <= 0) {
            this.validationHandler().append(new DomainError("'quantity' should be greater than zero"));
        }
    }
}
