package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.domain.client.ClientId;

import java.util.Objects;

public class ClientJpaMapper {

    private ClientJpaMapper() {
        // Prevent instantiation
    }

    public static Client fromJpa(final ClientJpaEntity clientJpa) {
        return Client.with(
                ClientId.of(clientJpa.getId()),
                clientJpa.getPublicId(),
                clientJpa.getName(),
                clientJpa.getEmail(),
                ClientCpf.of(clientJpa.getCpf()),
                clientJpa.getCreatedAt(),
                clientJpa.getUpdateAt(),
                clientJpa.getDeletedAt()
        );
    }

    public static ClientJpaEntity toJpa(final Client client) {
        if (Objects.isNull(client)) return new ClientJpaEntity();

        return new ClientJpaEntity(
                Objects.isNull(client.getId()) ? null : client.getId().getValue(),
                client.getPublicId(),
                client.getName(),
                client.getEmail(),
                Objects.isNull(client.getCpf()) ? null : client.getCpf().getValue(),
                client.getCreatedAt(),
                client.getUpdatedAt(),
                client.getDeletedAt()
        );
    }
}
