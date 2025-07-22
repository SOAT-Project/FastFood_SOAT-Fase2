package soat.project.fastfoodsoat.infrastructure.client.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ClientAuthRequest(
        @NotBlank(message = "field 'identification' is required")
        @JsonProperty("identification") String identification
) {
}
