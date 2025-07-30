package soat.project.fastfoodsoat.infrastructure.order.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateOrderStatusRequest(
        @NotBlank(message = "field 'status' is required")
        @JsonProperty("status") String status
) {
}