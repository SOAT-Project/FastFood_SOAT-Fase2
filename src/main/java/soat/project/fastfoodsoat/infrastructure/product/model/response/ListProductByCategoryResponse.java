package soat.project.fastfoodsoat.infrastructure.product.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record ListProductByCategoryResponse(
        @JsonProperty("id") Integer id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("image_url") String imageUrl,
        @JsonProperty("product_category_id") Integer productCategoryId,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
}