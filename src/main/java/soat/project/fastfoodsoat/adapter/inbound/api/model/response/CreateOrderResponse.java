package soat.project.fastfoodsoat.adapter.inbound.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderResponse(
        @JsonProperty("public_id") String publicId,
        @JsonProperty("order_number") Integer orderNumber,
        @JsonProperty("order_status") String status,
        @JsonProperty("value") String value,
        @JsonProperty("payment_status") String paymentStatus,
        @JsonProperty("payment_external_reference") String paymentExternalReference,
        @JsonProperty("qr_code") String qrCode,
        @JsonProperty("products") CreateOrderProductResponse[] orderProducts
) {
}
