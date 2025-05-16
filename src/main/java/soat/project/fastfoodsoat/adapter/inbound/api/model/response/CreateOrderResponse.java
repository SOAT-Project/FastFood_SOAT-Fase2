package soat.project.fastfoodsoat.adapter.inbound.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderResponse(
        @JsonProperty("public_id") String publicId,
        @JsonProperty("value") String value,
        @JsonProperty("order_number") Integer orderNumber,
        @JsonProperty("status") String status,
        @JsonProperty("order_products") CreateOrderProductResponse[] orderProducts,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt
) {
}
