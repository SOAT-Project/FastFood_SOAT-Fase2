package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.ClientJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.ProductMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ClientRepository;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.domain.client.ClientGateway;
import soat.project.fastfoodsoat.domain.product.Product;

import java.util.Optional;

@Component
public class ClientJpaGateway implements ClientGateway {
    private final ClientRepository clientRepository;

    public ClientJpaGateway(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Optional<Client> findByCpf(final ClientCpf cpf) {
        final var clientJpa = this.clientRepository.findByCpf(cpf.getValue());

        return composeClient(clientJpa);
    }

    @Override
    public Client create(Client client) {
        return save(client);
    }

    private Client save(final Client client){
        final var saveClient = clientRepository.save(ClientJpaMapper.fromDomain(client));
        return ClientJpaMapper.toDomain(saveClient);
    }

    private Optional<Client> composeClient(
            final Optional<ClientJpaEntity> clientJpa
    ) {
        if (clientJpa.isEmpty()) return Optional.empty();

        final var client = ClientJpaMapper.fromJpa(clientJpa.get());

        return Optional.of(client);
    }
}
