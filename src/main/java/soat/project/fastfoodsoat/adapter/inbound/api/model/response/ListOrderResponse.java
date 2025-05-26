package soat.project.fastfoodsoat.adapter.inbound.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ListOrderResponse(
        @JsonProperty("public_id") UUID publicId,
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("order_number") Integer orderNumber,
        @JsonProperty("status") String status,
        @JsonProperty("products") List<ListOrderProductResponse> products
) {
}
