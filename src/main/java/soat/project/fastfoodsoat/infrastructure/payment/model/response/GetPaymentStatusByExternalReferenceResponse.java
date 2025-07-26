package soat.project.fastfoodsoat.infrastructure.payment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPaymentStatusByExternalReferenceResponse(
        @JsonProperty("status") String paymentStatus
) {
}
