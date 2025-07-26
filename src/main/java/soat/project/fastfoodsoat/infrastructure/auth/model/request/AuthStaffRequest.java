package soat.project.fastfoodsoat.infrastructure.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AuthStaffRequest(
        @NotBlank(message = "field 'identification' is required")
        @JsonProperty("identification") String identification
) {
}
