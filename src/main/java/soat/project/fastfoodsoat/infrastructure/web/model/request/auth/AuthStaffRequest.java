package soat.project.fastfoodsoat.infrastructure.web.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthStaffRequest(
        @JsonProperty("identification") String identification
) {
}
