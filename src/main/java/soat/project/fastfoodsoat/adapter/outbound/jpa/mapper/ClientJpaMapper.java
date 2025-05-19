package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientCpf;
import soat.project.fastfoodsoat.domain.client.ClientId;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

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

    public static ClientJpaEntity fromDomain(final Client client){
        final Integer id = client.getId() != null ? client.getId().getValue() : null;
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

    public static Client toDomain(ClientJpaEntity clientJpaEntity) {
        return Client.with(
                ClientId.of(clientJpaEntity.getId()),
                clientJpaEntity.getPublicId(),
                clientJpaEntity.getName(),
                clientJpaEntity.getEmail(),
                ClientCpf.of(clientJpaEntity.getCpf()),
                clientJpaEntity.getCreatedAt(),
                clientJpaEntity.getUpdateAt(),
                clientJpaEntity.getDeletedAt()
        );
    }
}
