package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateProductResponse(
        @JsonProperty("id") Integer id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("image_url") String imageURL,
        @JsonProperty("product_category_id") Integer productCategoryId,
        @JsonProperty("updated_at") Instant updatedAt
) {
}