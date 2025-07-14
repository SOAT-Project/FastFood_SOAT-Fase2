package soat.project.fastfoodsoat.domain.productcategory;

import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.util.Objects;

public class ProductCategoryValidator extends Validator{

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 100;

    private final ProductCategory productCategory;

    protected ProductCategoryValidator(final ProductCategory productCategory, final ValidationHandler handler) {
        super(handler);
        this.productCategory = productCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.productCategory.getName();

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
            final var msg = "'name' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, NAME_MIN_LENGTH, NAME_MAX_LENGTH)));
        }
    }


}
