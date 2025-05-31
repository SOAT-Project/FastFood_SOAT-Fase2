package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.ClientJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ClientRepository;
import soat.project.fastfoodsoat.domain.client.*;

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
    public Optional<Client> findByPublicId(final ClientPublicId publicId) {
        return this.clientRepository.findByPublicId(publicId.getValue())
                .map(ClientJpaMapper::toDomain);
    }

    @Override
    public Client create(Client client) {
        return save(client);
    }

    @Override
    public Optional<Client> findById(ClientId id) {
        return this.clientRepository.findById(id.getValue())
                .map(ClientJpaMapper::toDomain);
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
