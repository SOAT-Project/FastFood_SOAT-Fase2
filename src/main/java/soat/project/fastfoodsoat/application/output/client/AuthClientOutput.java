package soat.project.fastfoodsoat.application.output.client;

import java.util.UUID;

public record AuthClientOutput(UUID publicId, String name, String email, String cpf) {
}
