package soat.project.fastfoodsoat.infrastructure.web.model.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("product_category_id") Integer productCategoryId
) {
}