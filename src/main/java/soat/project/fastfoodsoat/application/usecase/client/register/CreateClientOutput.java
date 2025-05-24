package soat.project.fastfoodsoat.application.usecase.client.register;

import soat.project.fastfoodsoat.domain.client.Client;

import java.util.UUID;

public record CreateClientOutput (
        UUID publicId,
        String name,
        String email,
        String CPF
) {

    public static CreateClientOutput from(
            final UUID publicId,
            final String name,
            final String email,
            final String CPF
    ) {
        return new CreateClientOutput(publicId, name, email, CPF);
    }

    public static CreateClientOutput from(final Client client) {
        return new CreateClientOutput(
                client.getPublicId(),
                client.getName(),
                client.getEmail(),
                client.getCpf().getValue()
        );
    }
}
