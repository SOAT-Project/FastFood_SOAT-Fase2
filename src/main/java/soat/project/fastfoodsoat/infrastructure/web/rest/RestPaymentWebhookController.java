package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.payment.controller.PaymentWebhookController;
import soat.project.fastfoodsoat.infrastructure.payment.model.request.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.MercadoPagoWebhookResponse;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.PaymentWebhookAPI;

@RestController
public class RestPaymentWebhookController implements PaymentWebhookAPI {

    private final PaymentWebhookController paymentWebhookController;

    public RestPaymentWebhookController(final PaymentWebhookController paymentWebhookController) {
        this.paymentWebhookController = paymentWebhookController;
    }

    @Override
    public ResponseEntity<MercadoPagoWebhookResponse> handlePaymentNotification(final MercadoPagoWebhookRequest request) {
        return ResponseEntity.ok(paymentWebhookController.handlePaymentNotification(request));
    }

    private String mapMercadoPagoTopicToStatus(String topic) {
        return switch (topic.toLowerCase()) {
            case "payment.created", "payment.updated" -> "APPROVED";

            default -> "REJECTED";
        };
    }
}
