package soat.project.fastfoodsoat.application.usecase.client.register;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientGateway;
import soat.project.fastfoodsoat.domain.exception.ConflictException;

import java.util.UUID;
import java.util.function.Consumer;

@Component
public class DefaultCreateClientUseCase extends CreateClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultCreateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public CreateClientOutput execute(CreateClientCommand command) {
        final var newClient = Client.newClient(UUID.randomUUID(), command.name(), command.email(), command.cpf());

        clientGateway.findByCpf(newClient.getCpf()).ifPresent(alreadyExist(newClient.getCpf().getValue()));

        return CreateClientOutput.from(
                this.clientGateway.create(newClient)
        );
    }

    private Consumer<? super Client> alreadyExist(final String cpf) {
        return client -> {
            throw ConflictException.with(Client.class, cpf);
        };
    }
}
