package soat.project.fastfoodsoat.application.gateway;

import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.domain.client.ClientId;
import soat.project.fastfoodsoat.domain.client.ClientPublicId;

import java.util.Optional;

public interface ClientRepositoryGateway {
    Optional<Client> findByCpf(ClientCpf cpf);
    Optional<Client> findByPublicId(ClientPublicId publicId);
    Client create(Client client);
    Optional<Client> findById(ClientId id);
}
