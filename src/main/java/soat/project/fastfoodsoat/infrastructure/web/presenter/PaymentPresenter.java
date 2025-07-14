package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.application.output.payment.GetPaymentStatusByExternalReferenceOutput;
import soat.project.fastfoodsoat.application.output.payment.UpdatePaymentStatusOutput;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.GetPaymentStatusByExternalReferenceResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.MercadoPagoWebhookResponse;

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
