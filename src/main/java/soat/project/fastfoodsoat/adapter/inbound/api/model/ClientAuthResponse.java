package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ClientAuthResponse(
        @JsonProperty("public_id") UUID publicId,
        @JsonProperty("name")  String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf")  String cpf
) {
}
