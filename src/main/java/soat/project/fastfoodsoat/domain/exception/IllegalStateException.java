package soat.project.fastfoodsoat.domain.exception;

import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.List;

public class IllegalStateException extends DomainException {

    protected IllegalStateException(final String aMessage, final List<DomainError> someErrors) {
        super(aMessage, someErrors);
    }

    public static DomainException with(final DomainError anError) {
        return new IllegalStateException(anError.message(), List.of(anError));
    }
}
