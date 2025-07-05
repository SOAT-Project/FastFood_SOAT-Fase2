package soat.project.fastfoodsoat.infrastructure.web.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "field 'name' is required")
        @JsonProperty("name") String name,

        @NotBlank(message = "field 'description' is required")
        @JsonProperty("description") String description,

        @NotNull(message = "field 'value' is required")
        @DecimalMin(value = "0.01", message = "field 'value' must be greater than or equal to 0.01")
        @JsonProperty("value") BigDecimal value,

        @JsonProperty("image_url") String imageUrl,

        @NotNull(message = "field 'product_category_id' is required")
        @Min(value = 1, message = "field 'product_category_id' must be greater than or equal to 1")
        @JsonProperty("product_category_id") Integer productCategoryId
) {
}