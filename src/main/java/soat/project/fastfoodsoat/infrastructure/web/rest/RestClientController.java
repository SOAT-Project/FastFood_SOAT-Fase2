package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.client.controller.ClientController;
import soat.project.fastfoodsoat.infrastructure.client.model.request.ClientAuthRequest;
import soat.project.fastfoodsoat.infrastructure.client.model.request.CreateClientRequest;
import soat.project.fastfoodsoat.infrastructure.client.model.response.ClientAuthResponse;
import soat.project.fastfoodsoat.infrastructure.client.model.response.CreateClientResponse;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.ClientAPI;

@RestController
public class RestClientController implements ClientAPI {

    private final ClientController clientFacade;

    public RestClientController(final ClientController clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Override
    public ResponseEntity<ClientAuthResponse> findClientByCPF(ClientAuthRequest clientAuthRequest) {
        return ResponseEntity.ok(this.clientFacade.authClient(clientAuthRequest));
    }

    @Override
    public ResponseEntity<CreateClientResponse> create(CreateClientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clientFacade.createClient(request));
    }

}
