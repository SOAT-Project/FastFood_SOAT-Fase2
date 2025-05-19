package soat.project.fastfoodsoat.adapter.inbound.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientRequest(
        @JsonProperty("name")  String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf")  String cpf
) {
}
