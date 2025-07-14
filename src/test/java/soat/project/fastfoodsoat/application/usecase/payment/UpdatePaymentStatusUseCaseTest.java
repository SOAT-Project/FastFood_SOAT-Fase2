package soat.project.fastfoodsoat.application.usecase.payment;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.command.payment.update.UpdatePaymentStatusCommand;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.IllegalStateException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePaymentStatusUseCaseTest extends UseCaseTest {

    @InjectMocks
    private UpdatePaymentStatusUseCaseImpl useCase;

    @Mock
    private PaymentRepositoryGateway paymentRepositoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(paymentRepositoryGateway);
    }

    @Test
    void givenValidCommandAndPaymentPendingOrderReceived_whenUpdateStatusToApproved_thenShouldUpdatePayment() {
        // Given
        final var externalReference = UUID.randomUUID().toString();
        final var command = new UpdatePaymentStatusCommand(externalReference, PaymentStatus.APPROVED.toString());

        final var mockPayment = mock(Payment.class);
        when(mockPayment.getExternalReference()).thenReturn(externalReference);
        when(mockPayment.getStatus()).thenReturn(PaymentStatus.PENDING);
        when(mockPayment.getUpdatedAt()).thenReturn(Instant.now());
        doNothing().when(mockPayment).updateStatus(PaymentStatus.APPROVED);

        when(paymentRepositoryGateway.findByExternalReference(externalReference)).thenReturn(Optional.of(mockPayment));
        when(paymentRepositoryGateway.update(mockPayment)).thenReturn(mockPayment);

        // When
        final var output = useCase.execute(command);

        // Then
        assertNotNull(output);
        assertEquals(externalReference, output.externalReference());

        verify(paymentRepositoryGateway, times(1)).findByExternalReference(externalReference);
        verify(mockPayment, times(1)).updateStatus(PaymentStatus.APPROVED);
        verify(paymentRepositoryGateway, times(1)).update(mockPayment);
    }

    @Test
    void givenValidCommandAndPaymentPendingOrderInPreparation_whenUpdateStatusToApproved_thenShouldUpdatePaymentOnly() {
        // Given
        final var externalReference = UUID.randomUUID().toString();
        final var command = new UpdatePaymentStatusCommand(externalReference, PaymentStatus.APPROVED.name());

        final var mockPayment = mock(Payment.class);
        when(mockPayment.getExternalReference()).thenReturn(externalReference);
        when(mockPayment.getStatus()).thenReturn(PaymentStatus.PENDING);
        when(mockPayment.getUpdatedAt()).thenReturn(Instant.now());
        doNothing().when(mockPayment).updateStatus(PaymentStatus.APPROVED);

        when(paymentRepositoryGateway.findByExternalReference(externalReference)).thenReturn(Optional.of(mockPayment));
        when(paymentRepositoryGateway.update(mockPayment)).thenReturn(mockPayment);

        // When
        final var output = useCase.execute(command);

        // Then
        assertNotNull(output);
        assertEquals(externalReference, output.externalReference());

        verify(paymentRepositoryGateway, times(1)).findByExternalReference(externalReference);
        verify(mockPayment, times(1)).updateStatus(PaymentStatus.APPROVED);
        verify(paymentRepositoryGateway, times(1)).update(mockPayment);
    }

    @Test
    void givenValidCommand_whenUpdateStatusToCanceled_thenShouldUpdatePayment() {
        // Given
        final var externalReference = UUID.randomUUID().toString();
        final var command = new UpdatePaymentStatusCommand(externalReference, PaymentStatus.CANCELLED.name());

        final var mockPayment = mock(Payment.class);
        when(mockPayment.getExternalReference()).thenReturn(externalReference);
        when(mockPayment.getStatus()).thenReturn(PaymentStatus.PENDING);
        when(mockPayment.getUpdatedAt()).thenReturn(Instant.now());
        doNothing().when(mockPayment).updateStatus(PaymentStatus.CANCELLED);

        when(paymentRepositoryGateway.findByExternalReference(externalReference)).thenReturn(Optional.of(mockPayment));
        when(paymentRepositoryGateway.update(mockPayment)).thenReturn(mockPayment);

        // When
        final var output = useCase.execute(command);

        // Then
        assertNotNull(output);
        assertEquals(externalReference, output.externalReference());

        verify(paymentRepositoryGateway, times(1)).findByExternalReference(externalReference);
        verify(mockPayment, times(1)).updateStatus(PaymentStatus.CANCELLED);
        verify(paymentRepositoryGateway, times(1)).update(mockPayment);
    }

    @Test
    void givenInvalidExternalReference_whenUpdateStatus_thenShouldThrowNotFoundException() {
        // Given
        final var externalReference = UUID.randomUUID().toString();
        final var command = new UpdatePaymentStatusCommand(externalReference, PaymentStatus.APPROVED.name());

        when(paymentRepositoryGateway.findByExternalReference(externalReference)).thenReturn(Optional.empty());

        // When & Then
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));
        assertTrue(exception.getMessage().contains("Payment not found for externalReference: " + externalReference));

        verify(paymentRepositoryGateway, times(1)).findByExternalReference(externalReference);
        verify(paymentRepositoryGateway, never()).update(any());
    }

    @Test
    void givenInvalidNewStatusString_whenUpdateStatus_thenShouldThrowIllegalStateException() {
        // Given
        final var externalReference = UUID.randomUUID().toString();
        final var command = new UpdatePaymentStatusCommand(externalReference, "INVALID_STATUS");

        // When & Then
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));
        assertTrue(exception.getMessage().contains("Status inválido: INVALID_STATUS"));

        verify(paymentRepositoryGateway, never()).findByExternalReference(any());
        verify(paymentRepositoryGateway, never()).update(any());
    }
}
