package soat.project.fastfoodsoat.application.usecase.client.register;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientGateway;

import java.util.UUID;

@Component
public class DefaultCreateClientUseCase extends CreateClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultCreateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public CreateClientOutput execute(CreateClientCommand command) {
        final var newClient = Client.newClient(UUID.randomUUID(), command.name(), command.email(), command.cpf());

        return CreateClientOutput.from(
                this.clientGateway.create(newClient)
        );
    }
}
