package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthStaffRequest(
        @JsonProperty("identification") String identification
) {
}
