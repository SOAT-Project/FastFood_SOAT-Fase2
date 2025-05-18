package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.adapter.inbound.api.ClientAPI;
import soat.project.fastfoodsoat.adapter.inbound.api.model.ClientAuthRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.ClientAuthResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.presenter.AuthClientPresenter;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientCommand;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientUseCase;

@RestController
public class ClientController implements ClientAPI {

    private final AuthClientUseCase authClientUseCase;

    public ClientController(AuthClientUseCase authClientUseCase) {
        this.authClientUseCase = authClientUseCase;
    }

    @Override
    public ResponseEntity<ClientAuthResponse> findClientByCPF(ClientAuthRequest clientAuthRequest) {
        final var command = new AuthClientCommand(clientAuthRequest.identification());

        final var output = this.authClientUseCase.execute(command);

        return ResponseEntity.ok(AuthClientPresenter.present(output));
    }

}
