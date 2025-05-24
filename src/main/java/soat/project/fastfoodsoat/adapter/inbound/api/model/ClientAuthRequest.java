package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientAuthRequest(
        @JsonProperty("identification") String identification
) {
}
