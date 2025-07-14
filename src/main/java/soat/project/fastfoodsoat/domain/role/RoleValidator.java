package soat.project.fastfoodsoat.domain.role;

import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.util.Objects;

public class RoleValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 100;
    private static final Integer NAME_MIN_LENGTH = 3;

    private final Role role;

    protected RoleValidator(final Role role, final ValidationHandler handler) {
        super(handler);
        this.role = role;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.role.getName();
        if (Objects.isNull(name)) {
            this.validationHandler().append(new DomainError("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new DomainError("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            final String msg = "'name' must be between %d and %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, NAME_MIN_LENGTH, NAME_MAX_LENGTH)));
        }
    }

}
