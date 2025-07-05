package soat.project.fastfoodsoat.infrastructure.web.model.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOrderStatusRequest(
        @JsonProperty("status") String status
) {
}