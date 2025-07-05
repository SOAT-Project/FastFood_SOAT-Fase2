package soat.project.fastfoodsoat.infrastructure.web.model.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientAuthRequest(
        @JsonProperty("identification") String identification
) {
}
