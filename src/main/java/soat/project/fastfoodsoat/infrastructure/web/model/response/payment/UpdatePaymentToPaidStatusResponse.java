package soat.project.fastfoodsoat.infrastructure.web.model.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePaymentToPaidStatusResponse(
        @JsonProperty("value") String value,
        @JsonProperty("status") String paymentStatus,
        @JsonProperty("external_reference") String externalReference
) {
}
