package soat.project.fastfoodsoat.application.usecase.payment.update.status;

import soat.project.fastfoodsoat.application.command.payment.update.UpdatePaymentStatusCommand;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.application.output.payment.UpdatePaymentStatusOutput;
import soat.project.fastfoodsoat.domain.exception.IllegalStateException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.domain.validation.DomainError;

public class UpdatePaymentStatusUseCaseImpl extends UpdatePaymentStatusUseCase {

    private final PaymentRepositoryGateway paymentRepositoryGateway;

    public UpdatePaymentStatusUseCaseImpl(
            final PaymentRepositoryGateway paymentRepositoryGateway
    ) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
    }

    @Override
    public UpdatePaymentStatusOutput execute(UpdatePaymentStatusCommand command) {
        final var externalReference = command.externalReference();
        final var newStatus = validateStatus(command.newStatus());

        final Payment payment = paymentRepositoryGateway.findByExternalReference(externalReference)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for externalReference: " + externalReference)));

        payment.updateStatus(newStatus);

        final Payment updatedPayment = paymentRepositoryGateway.update(payment);

        return UpdatePaymentStatusOutput.from(
                updatedPayment.getExternalReference(),
                updatedPayment.getStatus(),
                updatedPayment.getUpdatedAt()
        );
    }

    public PaymentStatus validateStatus(String status) {
        try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw IllegalStateException.with(new DomainError("Status inválido: " + status));
        }
    }
}
