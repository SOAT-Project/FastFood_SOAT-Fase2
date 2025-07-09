package soat.project.fastfoodsoat.application.usecase.payment;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceCommand;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GetPaymentStatusByExternalReferenceUseCaseTest extends UseCaseTest {

    @InjectMocks
    private GetPaymentStatusByExternalReferenceUseCaseImpl useCase;

    @Mock
    private PaymentRepositoryGateway paymentRepositoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(paymentRepositoryGateway);
    }

    @Test
    void givenValidCommand_whenGetPaymentStatusByExternalReferenceUseCase_thenShouldReturnStatus() {
        final var command = new GetPaymentStatusByExternalReferenceCommand("123456789");

        final var payment = mock(Payment.class);
        when(payment.getStatus()).thenReturn(PaymentStatus.PENDING);

        when(paymentRepositoryGateway.findByExternalReference(command.externalReference())).thenReturn(java.util.Optional.of(payment));

        final var output = useCase.execute(command);

        assertNotNull(output);
        verify(paymentRepositoryGateway, times(1)).findByExternalReference(command.externalReference());
    }

    @Test
    void givenInvalidCommand_whenGetPaymentStatusByExternalReferenceUseCase_thenShouldThrowNotFoundException() {
        final var command = new GetPaymentStatusByExternalReferenceCommand("123456789");

        when(paymentRepositoryGateway.findByExternalReference(command.externalReference())).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
        verify(paymentRepositoryGateway, times(1)).findByExternalReference(command.externalReference());
    }
}
