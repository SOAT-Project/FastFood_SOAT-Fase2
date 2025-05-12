package soat.project.fastfoodsoat.domain.product;

import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductValidator extends Validator {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int DESCRIPTION_MAX_LENGTH = 255;

    private final Product product;

    protected ProductValidator(final Product product, final ValidationHandler handler) {
        super(handler);
        this.product = product;
    }

    @Override
    public void validate() {
        checkName();
        checkDescription();
        checkValue();
        checkImageUrl();
        checkProductCategorieId();
    }

    private void checkName() {
        final var name = product.getName();

        if (Objects.isNull(name)) {
            this.validationHandler().append(new DomainError("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new DomainError("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            this.validationHandler().append(new DomainError(
                    String.format("'name' must be between %d and %d characters", NAME_MIN_LENGTH, NAME_MAX_LENGTH)
            ));
        }
    }

    private void checkDescription() {
        final var description = product.getDescription();

        if (Objects.isNull(description)) {
            this.validationHandler().append(new DomainError("'description' should not be null"));
            return;
        }

        if (description.isBlank()) {
            this.validationHandler().append(new DomainError("'description' should not be empty"));
            return;
        }

        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            this.validationHandler().append(new DomainError(
                    String.format("'description' must not exceed %d characters", DESCRIPTION_MAX_LENGTH)
            ));
        }
    }

    private void checkValue() {
        final BigDecimal value = product.getValue();

        if (Objects.isNull(value)) {
            this.validationHandler().append(new DomainError("'value' should not be null"));
            return;
        }

        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            this.validationHandler().append(new DomainError("'value' must be greater than zero"));
        }
    }

    private void checkImageUrl() {
        final var imageURL = product.getImageURL();

        if (Objects.isNull(imageURL)) {
            this.validationHandler().append(new DomainError("'imageURL' should not be null"));
            return;
        }

        if (imageURL.isBlank()) {
            this.validationHandler().append(new DomainError("'imageURL' should not be empty"));
        }
    }

    private void checkProductCategorieId() {
        if (Objects.isNull(product.getProductCategoryId())) {
            this.validationHandler().append(new DomainError("'productCategorieId' should not be null"));
        }
    }
}
