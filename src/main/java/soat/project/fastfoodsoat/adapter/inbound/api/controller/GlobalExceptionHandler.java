package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soat.project.fastfoodsoat.adapter.inbound.api.model.DefaultApiError;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.utils.InstantUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<DefaultApiError> handleNotFoundException(final NotFoundException ex) {
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<DefaultApiError> handleDomainException(final DomainException ex) {
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                ex.getErrors()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<DefaultApiError> handleException(final Exception ex) {
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<DefaultApiError> handleRuntimeException(final RuntimeException ex) {
        final var error = new DefaultApiError(
                InstantUtils.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
