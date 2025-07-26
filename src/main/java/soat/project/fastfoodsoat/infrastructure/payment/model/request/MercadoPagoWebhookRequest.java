package soat.project.fastfoodsoat.infrastructure.payment.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MercadoPagoWebhookRequest(
        @NotBlank
        @Schema(description = "field 'external_reference' of payment")
        @JsonProperty("id") String id,

        @NotBlank
        @Schema(description = "status of payment (e.g., 'payment.update', 'payment.created' or 'payment.rejected')")
        @JsonProperty("topic")  String topic,

        @NotBlank
        @Schema(description = "resource URL of the payment (e.g., 'http://mock-url.com/payment/{external_reference}')")
        @JsonProperty("resource") String resource
) {}
