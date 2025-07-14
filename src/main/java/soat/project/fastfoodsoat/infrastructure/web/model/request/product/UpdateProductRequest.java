package soat.project.fastfoodsoat.infrastructure.web.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @JsonProperty("name") String name,

        @JsonProperty("description") String description,

        @DecimalMin(value = "0.01", message = "field 'value' must be greater than or equal to 0.01")
        @JsonProperty("value") BigDecimal value,

        @JsonProperty("image_url") String imageUrl,

        @Min(value = 1, message = "field 'product_category_id' must be greater than or equal to 1")
        @JsonProperty("product_category_id") Integer productCategoryId
) {
}