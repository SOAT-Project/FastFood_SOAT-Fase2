package soat.project.fastfoodsoat.application.usecase.client.auth;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.domain.client.ClientGateway;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.Objects;

@Component
public class DefaultAuthClientUseCase extends AuthClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultAuthClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public AuthClientOutput execute(AuthClientCommand command) {
        final var identification = command.identification();

        if (Objects.isNull(identification) || identification.isBlank()) {
            throw DomainException.with(new DomainError("invalid identification"));
        }

        final var client = retrieveClient(identification);

        return new AuthClientOutput(
                client.getPublicId(),
                client.getEmail(),
                client.getName(),
                client.getEmail()
        );

    }

    private Client retrieveClient(final String identification) {
        var client = this.clientGateway.findByCpf(ClientCpf.of(identification));

        if (client.isPresent()) return client.get();

        throw NotFoundException.with(new DomainError("client not found"));
    }
}

