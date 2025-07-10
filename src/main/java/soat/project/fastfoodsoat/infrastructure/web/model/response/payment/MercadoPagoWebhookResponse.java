package soat.project.fastfoodsoat.infrastructure.web.model.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MercadoPagoWebhookResponse(
        @JsonProperty("payment_status") String paymentStatus,
        @JsonProperty("external_reference") String externalReference
) {
}
