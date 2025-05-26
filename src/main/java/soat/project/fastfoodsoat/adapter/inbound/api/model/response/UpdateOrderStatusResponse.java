package soat.project.fastfoodsoat.adapter.inbound.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOrderStatusResponse(
        @JsonProperty("public_id") String publicId,
        @JsonProperty("status") String status
) {
}
