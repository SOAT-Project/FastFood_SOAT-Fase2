package soat.project.fastfoodsoat.infrastructure.payment.presenter;

import soat.project.fastfoodsoat.application.output.payment.GetPaymentStatusByExternalReferenceOutput;
import soat.project.fastfoodsoat.application.output.payment.UpdatePaymentStatusOutput;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.GetPaymentStatusByExternalReferenceResponse;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.MercadoPagoWebhookResponse;

public interface PaymentPresenter {

    static GetPaymentStatusByExternalReferenceResponse present(final GetPaymentStatusByExternalReferenceOutput output) {
        return new GetPaymentStatusByExternalReferenceResponse(
                output.paymentStatus()
        );
    }

    static MercadoPagoWebhookResponse present(final UpdatePaymentStatusOutput output) {
        return new MercadoPagoWebhookResponse(
                output.newPaymentStatus().name(),
                output.externalReference()
        );
    }
}
