package soat.project.fastfoodsoat.infrastructure.web.model.response.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPaymentStatusByExternalReferenceResponse(
        @JsonProperty("status") String paymentStatus
) {
}
