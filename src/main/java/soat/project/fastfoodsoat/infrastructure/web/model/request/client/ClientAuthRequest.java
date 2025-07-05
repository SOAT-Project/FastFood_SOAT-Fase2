package soat.project.fastfoodsoat.infrastructure.web.model.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ClientAuthRequest(
        @NotBlank(message = "field 'identification' is required")
        @JsonProperty("identification") String identification
) {
}
