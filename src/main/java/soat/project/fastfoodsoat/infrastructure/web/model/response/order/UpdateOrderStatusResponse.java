package soat.project.fastfoodsoat.infrastructure.web.model.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOrderStatusResponse(
        @JsonProperty("public_id") String publicId,
        @JsonProperty("status") String status
) {
}
