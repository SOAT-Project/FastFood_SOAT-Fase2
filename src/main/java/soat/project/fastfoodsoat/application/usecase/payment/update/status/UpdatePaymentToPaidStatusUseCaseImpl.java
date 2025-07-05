package soat.project.fastfoodsoat.application.usecase.payment.update.status;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.UpdatePaymentToPaidStatusCommand;
import soat.project.fastfoodsoat.application.output.payment.UpdatePaymentToPaidStatusOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.validation.DomainError;

@Transactional
@Component
public class UpdatePaymentToPaidStatusUseCaseImpl extends UpdatePaymentToPaidStatusUseCase {

    private final PaymentRepositoryGateway paymentRepositoryGateway;

    public UpdatePaymentToPaidStatusUseCaseImpl(PaymentRepositoryGateway paymentRepositoryGateway) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
    }

    @Override
    public UpdatePaymentToPaidStatusOutput execute(final UpdatePaymentToPaidStatusCommand command){
        final String externalReference = command.externalReference();

        final Payment payment = paymentRepositoryGateway.findByExternalReference(externalReference)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for externalReference: " + externalReference)));

        if (payment.getStatus() == PaymentStatus.APPROVED) {
            throw new IllegalStateException("Payment already approved");
        }

        payment.update(
                payment.getValue(),
                payment.getExternalReference(),
                payment.getQrCode(),
                PaymentStatus.APPROVED
        );

        paymentRepositoryGateway.update(payment);

        return UpdatePaymentToPaidStatusOutput.from(
                payment.getValue(),
                payment.getStatus().toString(),
                payment.getExternalReference()
        );
    }
}
