package soat.project.fastfoodsoat.infrastructure.payment.controller;

import soat.project.fastfoodsoat.infrastructure.payment.model.request.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.MercadoPagoWebhookResponse;

public interface PaymentWebhookController {
    MercadoPagoWebhookResponse handlePaymentNotification(MercadoPagoWebhookRequest request);
}
