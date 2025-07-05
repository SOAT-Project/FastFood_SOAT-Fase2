package soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.GetQRCodeCommand;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.shared.utils.QRCodeGeneratorUtils;

@Transactional
@Component
public class GetQRCodeUseCaseImpl extends GetQRCodeUseCase {

    private final PaymentRepositoryGateway paymentRepositoryGateway;

    public GetQRCodeUseCaseImpl(PaymentRepositoryGateway paymentRepositoryGateway) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
    }

    @Override
    public String execute(GetQRCodeCommand command) {
        final String externalReference = command.externalReference();

        final Payment payment = paymentRepositoryGateway.findByExternalReference(externalReference)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for externalReference: " + externalReference)));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("Payment is not pending");
        }

        return QRCodeGeneratorUtils.generateQRCodeImage(
                payment.getQrCode(),
                300,
                300
        );
    }
}
