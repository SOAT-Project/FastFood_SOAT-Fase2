package soat.project.fastfoodsoat.infrastructure.web.model.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePaymentToPaidStatusRequest(
        @JsonProperty("external_reference") String externalReference
) {
}
