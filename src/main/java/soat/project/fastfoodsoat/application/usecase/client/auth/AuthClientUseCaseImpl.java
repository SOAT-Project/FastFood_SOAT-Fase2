package soat.project.fastfoodsoat.application.usecase.client.auth;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.client.auth.AuthClientCommand;
import soat.project.fastfoodsoat.application.output.client.auth.AuthClientOutput;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.application.gateway.ClientRepositoryGateway;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.validation.DomainError;

import java.util.Objects;

@Component
public class AuthClientUseCaseImpl extends AuthClientUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;

    public AuthClientUseCaseImpl(ClientRepositoryGateway clientRepositoryGateway) {
        this.clientRepositoryGateway = clientRepositoryGateway;
    }

    @Override
    public AuthClientOutput execute(AuthClientCommand command) {
        final var identification = command.identification();

        if (Objects.isNull(identification) || identification.isBlank()) {
            throw DomainException.with(new DomainError("invalid identification"));
        }

        final var client = retrieveClient(identification);

        return new AuthClientOutput(
                client.getPublicId().getValue(),
                client.getName(),
                client.getEmail(),
                client.getCpf().getValue()
        );

    }

    private Client retrieveClient(final String identification) {
        var client = this.clientRepositoryGateway.findByCpf(ClientCpf.of(identification));

        if (client.isPresent()) return client.get();

        throw NotFoundException.with(new DomainError("client not found"));
    }
}

