package soat.project.fastfoodsoat.infrastructure.order.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateOrderProductRequest(
        @NotNull(message = "field 'product_id' is required")
        @Min(value = 1, message = "field 'product_id' must be greater than or equal to 1")
        @JsonProperty("product_id") Integer productId,

        @NotNull(message = "field 'quantity' is required")
        @Min(value = 1, message = "field 'quantity' must be greater than or equal to 1")
        @JsonProperty("quantity") Integer quantity
) {
}
