package soat.project.fastfoodsoat.adapter.inbound.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOrderStatusRequest(
        @JsonProperty("status") String status
) {
}