package soat.project.fastfoodsoat.infrastructure.payment.controller;

import soat.project.fastfoodsoat.infrastructure.payment.model.response.GetPaymentStatusByExternalReferenceResponse;

public interface PaymentController {

    byte[] getQrCodeByExternalReference(String externalReference);

    GetPaymentStatusByExternalReferenceResponse getStatusByExternalReference(String externalReference);
}
