package soat.project.fastfoodsoat.infrastructure.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.PaymentWebhookAPI;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.PaymentWebhookMockAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.payment.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.MercadoPagoWebhookResponse;

@RestController
public class PaymentWebhookMockController implements PaymentWebhookMockAPI {

    private final PaymentWebhookAPI paymentWebhookAPI;

    public PaymentWebhookMockController(final PaymentWebhookAPI paymentWebhookAPI) {
        this.paymentWebhookAPI = paymentWebhookAPI;
    }

    @PostMapping("/simulate-mercadopago-webhook")
    public ResponseEntity<MercadoPagoWebhookResponse> simulateMercadoPagoWebhook(@RequestBody MercadoPagoWebhookRequest request) {
        return paymentWebhookAPI.handlePaymentNotification(request);
    }
}
