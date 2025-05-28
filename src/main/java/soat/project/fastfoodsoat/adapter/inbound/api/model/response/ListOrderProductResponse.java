package soat.project.fastfoodsoat.adapter.inbound.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ListOrderProductResponse(
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("quantity") Integer quantity,
        @JsonProperty("product_id") Integer productId,
        @JsonProperty("product_name") String productName
) {
}
