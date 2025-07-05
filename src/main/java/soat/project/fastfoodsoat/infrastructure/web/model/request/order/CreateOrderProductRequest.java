package soat.project.fastfoodsoat.infrastructure.web.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderProductRequest(
        @JsonProperty("product_id") Integer productId,
        @JsonProperty("quantity") Integer quantity
) {
}
