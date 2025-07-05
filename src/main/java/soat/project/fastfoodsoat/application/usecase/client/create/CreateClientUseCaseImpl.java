package soat.project.fastfoodsoat.application.usecase.client.create;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.client.CreateClientCommand;
import soat.project.fastfoodsoat.application.output.client.CreateClientOutput;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.application.gateway.ClientRepositoryGateway;
import soat.project.fastfoodsoat.domain.client.ClientPublicId;
import soat.project.fastfoodsoat.domain.exception.ConflictException;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class CreateClientUseCaseImpl extends CreateClientUseCase {

    private final ClientRepositoryGateway clientRepositoryGateway;

    public CreateClientUseCaseImpl(ClientRepositoryGateway clientRepositoryGateway) {
        this.clientRepositoryGateway = clientRepositoryGateway;
    }

    @Override
    public CreateClientOutput execute(CreateClientCommand command) {
        final var newClient = Client.newClient(ClientPublicId.of(UUID.randomUUID()), command.name(), command.email(), command.cpf());

        clientRepositoryGateway.findByCpf(newClient.getCpf()).ifPresent(alreadyExist(newClient.getCpf().getValue()));

        return CreateClientOutput.from(
                this.clientRepositoryGateway.create(newClient)
        );
    }

    private Consumer<? super Client> alreadyExist(final String cpf) {
        return client -> {
            throw ConflictException.with(Client.class, cpf);
        };
    }
}
