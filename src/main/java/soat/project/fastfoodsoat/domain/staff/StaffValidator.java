package soat.project.fastfoodsoat.domain.staff;

import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.Validator;

import java.util.Objects;

public class StaffValidator extends Validator {

    private static final Integer NAME_MAX_LENGTH = 100;
    private static final Integer NAME_MIN_LENGTH = 3;

    private static final Integer EMAIL_MAX_LENGTH = 100;
    private static final String EMAIL_FORMAT_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Integer CPF_LENGTH = 11;

    private final Staff staff;

    public StaffValidator(final Staff staff, final ValidationHandler handler) {
        super(handler);
        this.staff = staff;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkCpfConstraints();
        checkEmailConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.staff.getName();
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

    private void checkEmailConstraints() {
        final var email = this.staff.getEmail();
        if (Objects.isNull(email)) {
            this.validationHandler().append(new DomainError("'email' should not be null"));
            return;
        }

        if (email.isBlank()) {
            this.validationHandler().append(new DomainError("'email' should not be empty"));
            return;
        }

        if (!email.matches(EMAIL_FORMAT_REGEX)) {
            this.validationHandler().append(new DomainError("'email' format is not valid"));
        }

        final int length = email.trim().length();
        if (length > EMAIL_MAX_LENGTH) {
            final String msg = "'email' size limit is %d characters";
            this.validationHandler().append(new DomainError(String.format(msg, EMAIL_MAX_LENGTH)));
        }
    }

    private void checkCpfConstraints() {
        final String cpf = staff.getCpf().getValue();

        if (Objects.isNull(cpf)) {
            this.validationHandler().append(new DomainError("'cpf' should not be null"));
            return;
        }

        if (cpf.isBlank()) {
            this.validationHandler().append(new DomainError("'cpf' should not be empty"));
            return;
        }

        if (!cpf.matches("^\\d+$")) {
            this.validationHandler().append(new DomainError("'cpf' should contain only digits"));
            return;
        }

        final int length = cpf.trim().length();
        if (length != CPF_LENGTH) {
            final String msg = "'cpf' size must be %d digits";
            this.validationHandler().append(new DomainError(String.format(msg, CPF_LENGTH)));
        }
    }
}
