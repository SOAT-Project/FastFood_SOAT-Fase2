package soat.project.fastfoodsoat.infrastructure.web.model.request.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateClientRequest(
        @NotBlank(message = "field 'name' is required")
        @JsonProperty("name")  String name,

        @NotBlank(message = "field 'email' is required")
        @Email(message = "field 'email' must be a valid email address")
        @JsonProperty("email") String email,

        @NotBlank(message = "field 'cpf' is required")
        @Size(min = 11, max = 11, message = "field 'cpf' must be exactly 11 characters long")
        @JsonProperty("cpf")  String cpf
) {
}
