package soat.project.fastfoodsoat.application.usecase.client.auth;

import java.util.UUID;

public record AuthClientOutput(UUID publicId, String name, String email, String cpf) {
}
