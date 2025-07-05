package soat.project.fastfoodsoat.infrastructure.web.model.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientRequest(
        @JsonProperty("name")  String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf")  String cpf
) {
}
