package soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode.GetQRCodeCommand;
import soat.project.fastfoodsoat.application.gateway.QRCodeServiceGateway;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import java.util.Base64;

@Transactional
@Component
public class GetQRCodeUseCaseImpl extends GetQRCodeUseCase {

    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final QRCodeServiceGateway qrCodeServiceGateway;

    public GetQRCodeUseCaseImpl(PaymentRepositoryGateway paymentRepositoryGateway, QRCodeServiceGateway qrCodeServiceGateway) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.qrCodeServiceGateway = qrCodeServiceGateway;
    }

    @Override
    public String execute(GetQRCodeCommand command) {
        final String externalReference = command.externalReference();

        final Payment payment = paymentRepositoryGateway.findByExternalReference(externalReference)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for externalReference: " + externalReference)));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not pending");
        }

        final byte[] qrCodeBytes = qrCodeServiceGateway.generateQRCodeImage(
            payment.getQrCode(),
            300,
            300
        );

        return Base64.getEncoder().encodeToString(qrCodeBytes);
    }
}
