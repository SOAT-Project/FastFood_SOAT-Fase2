package soat.project.fastfoodsoat.infrastructure.client.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreateClientResponse(
        @JsonProperty("public_id") UUID publicId,
        @JsonProperty("name")  String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf")  String cpf
) {
}
