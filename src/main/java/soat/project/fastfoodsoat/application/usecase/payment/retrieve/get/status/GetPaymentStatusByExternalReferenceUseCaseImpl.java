package soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceCommand;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.application.output.payment.GetPaymentStatusByExternalReferenceOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.validation.DomainError;

@Component
public class GetPaymentStatusByExternalReferenceUseCaseImpl extends GetPaymentStatusByExternalReferenceUseCase {

    private final PaymentRepositoryGateway paymentRepositoryGateway;

    public GetPaymentStatusByExternalReferenceUseCaseImpl(PaymentRepositoryGateway paymentRepositoryGateway) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
    }

    @Override
    public GetPaymentStatusByExternalReferenceOutput execute(GetPaymentStatusByExternalReferenceCommand command) {
        final String externalReference = command.externalReference();

        final Payment payment = paymentRepositoryGateway.findByExternalReference(externalReference)
                .orElseThrow(() -> NotFoundException.with(new DomainError("Payment not found for externalReference: " + externalReference)));

        return GetPaymentStatusByExternalReferenceOutput.from(payment.getStatus().toString());
    }
}
