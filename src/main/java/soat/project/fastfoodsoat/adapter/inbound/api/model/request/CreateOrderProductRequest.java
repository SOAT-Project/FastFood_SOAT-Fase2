package soat.project.fastfoodsoat.adapter.inbound.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderProductRequest(
        @JsonProperty("product_id") Integer productId,
        @JsonProperty("quantity") Integer quantity
) {
}
