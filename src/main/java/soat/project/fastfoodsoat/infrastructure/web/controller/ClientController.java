package soat.project.fastfoodsoat.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.ClientAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.client.ClientAuthRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.client.ClientAuthResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.request.client.CreateClientRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.client.CreateClientResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.AuthClientPresenter;
import soat.project.fastfoodsoat.infrastructure.web.presenter.ClientPresenter;
import soat.project.fastfoodsoat.application.command.client.auth.AuthClientCommand;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientUseCase;
import soat.project.fastfoodsoat.application.command.client.create.CreateClientCommand;
import soat.project.fastfoodsoat.application.usecase.client.create.CreateClientUseCase;

@RestController
public class ClientController implements ClientAPI {

    private final AuthClientUseCase authClientUseCase;
    private final CreateClientUseCase createClientUseCase;

    public ClientController(
            final AuthClientUseCase authClientUseCase,
            final CreateClientUseCase createClientUseCase
            ) {
        this.authClientUseCase = authClientUseCase;
        this.createClientUseCase = createClientUseCase;
    }

    @Override
    public ResponseEntity<ClientAuthResponse> findClientByCPF(ClientAuthRequest clientAuthRequest) {
        final var command = new AuthClientCommand(clientAuthRequest.identification());

        final var output = this.authClientUseCase.execute(command);

        return ResponseEntity.ok(AuthClientPresenter.present(output));
    }

    @Override
    public ResponseEntity<CreateClientResponse> create(CreateClientRequest request) {
        var command = new CreateClientCommand(request.name(), request.email(), request.cpf());

        var output = createClientUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClientPresenter.present(output));
    }

}
