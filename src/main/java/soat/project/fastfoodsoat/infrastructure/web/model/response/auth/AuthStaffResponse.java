package soat.project.fastfoodsoat.infrastructure.web.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthStaffResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") Long expiresIn
) {
}
