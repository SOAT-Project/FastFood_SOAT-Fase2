package soat.project.fastfoodsoat.infrastructure.client.controller;

import soat.project.fastfoodsoat.infrastructure.client.model.request.ClientAuthRequest;
import soat.project.fastfoodsoat.infrastructure.client.model.request.CreateClientRequest;
import soat.project.fastfoodsoat.infrastructure.client.model.response.ClientAuthResponse;
import soat.project.fastfoodsoat.infrastructure.client.model.response.CreateClientResponse;

public interface ClientController {
    ClientAuthResponse authClient(ClientAuthRequest request);
    CreateClientResponse createClient(CreateClientRequest request);
}
