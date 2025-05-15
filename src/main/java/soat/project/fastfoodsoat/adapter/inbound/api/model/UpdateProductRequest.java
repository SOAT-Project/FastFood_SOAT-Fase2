package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("value") BigDecimal value,
        @JsonProperty("image_URL") String image_URL,
        @JsonProperty("productCategoryId") Integer productCategoryId
) {
}