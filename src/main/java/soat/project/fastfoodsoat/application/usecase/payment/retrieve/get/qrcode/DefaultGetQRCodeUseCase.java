package soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentGateway;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.utils.QRCodeGeneratorUtils;

import java.util.UUID;

@Transactional
@Component
public class DefaultGetQRCodeUseCase extends GetQRCodeUseCase {

    private final PaymentGateway paymentGateway;

    public DefaultGetQRCodeUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @Override
    public String execute(GetQRCodeCommand command) {
        final UUID orderPublicId = command.orderPublicId();

        final Payment payment = paymentGateway.findByOrderPublicId(orderPublicId)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for order with public ID: " + orderPublicId)));

        return QRCodeGeneratorUtils.generateQRCodeImage(
                payment.getQrCode(),
                300,
                300
        );
    }
}
