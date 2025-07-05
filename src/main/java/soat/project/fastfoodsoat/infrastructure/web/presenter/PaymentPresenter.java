package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.UpdatePaymentToPaidStatusResponse;
import soat.project.fastfoodsoat.application.output.payment.UpdatePaymentToPaidStatusOutput;

public interface PaymentPresenter {

    static UpdatePaymentToPaidStatusResponse present(final UpdatePaymentToPaidStatusOutput output) {
        return new UpdatePaymentToPaidStatusResponse(
                output.value().toString(),
                output.paymentStatus(),
                output.externalReference()
        );
    }
}
