package soat.project.fastfoodsoat.application.usecase.payment;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.gateway.QRCodeServiceGateway;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetPaymentQRCodeByExternalReferenceUseCaseImpl;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode.GetPaymentQRCodeCommand;
import soat.project.fastfoodsoat.domain.exception.IllegalStateException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPaymentQRCodeUseCaseTest extends UseCaseTest  {

    @InjectMocks
    private GetPaymentQRCodeByExternalReferenceUseCaseImpl useCase;

    @Mock
    private PaymentRepositoryGateway paymentRepositoryGateway;

    @Mock
    private QRCodeServiceGateway qrCodeServiceGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(paymentRepositoryGateway, qrCodeServiceGateway);
    }

    @Test
    void givenValidCommand_whenGetQRCodeUseCase_thenShouldReturnQRCode() {
        final var command = new GetPaymentQRCodeCommand("123456789");

        final var payment = mock(Payment.class);
        when(payment.getStatus()).thenReturn(PaymentStatus.PENDING);
        when(payment.getQrCode()).thenReturn("QRCode");
        when(paymentRepositoryGateway.findByExternalReference(command.externalReference())).thenReturn(java.util.Optional.of(payment));
        when(qrCodeServiceGateway.generateQRCodeImage(anyString(), anyInt(), anyInt())).thenReturn("QRCode".getBytes());

        final var output = useCase.execute(command);

        assertNotNull(output);
        verify(paymentRepositoryGateway, times(1)).findByExternalReference(command.externalReference());
    }

    @Test
    void givenInvalidCommand_whenGetQRCodeUseCase_thenShouldThrowNotFoundException() {
        final var command = new GetPaymentQRCodeCommand("123456789");

        when(paymentRepositoryGateway.findByExternalReference(command.externalReference())).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
        verify(paymentRepositoryGateway, times(1)).findByExternalReference(command.externalReference());
    }

    @Test
    void givenPaymentNotPending_whenGetQRCodeUseCase_thenShouldThrowIllegalStateException() {
        final var command = new GetPaymentQRCodeCommand("123456789");

        final var payment = mock(Payment.class);
        when(payment.getStatus()).thenReturn(PaymentStatus.APPROVED);
        when(paymentRepositoryGateway.findByExternalReference(command.externalReference())).thenReturn(java.util.Optional.of(payment));

        assertThrows(IllegalStateException.class, () -> useCase.execute(command));
        verify(paymentRepositoryGateway, times(1)).findByExternalReference(command.externalReference());
    }
}