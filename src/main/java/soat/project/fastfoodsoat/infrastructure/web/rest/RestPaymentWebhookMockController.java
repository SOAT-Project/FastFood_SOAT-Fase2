package soat.project.fastfoodsoat.infrastructure.web.rest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.payment.model.request.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.MercadoPagoWebhookResponse;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.PaymentWebhookAPI;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.PaymentWebhookMockAPI;

@RestController
public class RestPaymentWebhookMockController implements PaymentWebhookMockAPI {

    private final PaymentWebhookAPI paymentWebhookAPI;

    public RestPaymentWebhookMockController(final PaymentWebhookAPI paymentWebhookAPI) {
        this.paymentWebhookAPI = paymentWebhookAPI;
    }

    @PostMapping("/simulate-mercadopago-webhook")
    public ResponseEntity<MercadoPagoWebhookResponse> simulateMercadoPagoWebhook(@RequestBody MercadoPagoWebhookRequest request) {
        return paymentWebhookAPI.handlePaymentNotification(request);
    }
}
