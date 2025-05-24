package soat.project.fastfoodsoat.domain.client;

import soat.project.fastfoodsoat.domain.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;
import soat.project.fastfoodsoat.utils.InstantUtils;

import java.time.Instant;
import java.util.UUID;

public class Client extends AggregateRoot<ClientId> {

    private UUID publicId;
    private String name;
    private String email;
    private ClientCpf cpf;

    private Client(
            final ClientId id,
            final UUID publicId,
            final String name,
            final String email,
            final ClientCpf cpf,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(
                id,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.publicId = publicId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.selfValidation();
    }

    public static Client newClient(
            final UUID publicId,
            final String name,
            final String email,
            final String cpf
    ) {
        final ClientId clientId = null; // O valor do ID será na camada de persistência
        final ClientCpf clientCpf = ClientCpf.of(cpf);
        final Instant now = InstantUtils.now();
        return new Client(
                clientId,
                publicId,
                name,
                email,
                clientCpf,
                now,
                now,
                null
        );
    }

    public static Client with(
            final ClientId id,
            final UUID publicId,
            final String name,
            final String email,
            final ClientCpf cpf,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Client(
            id,
            publicId,
            name,
            email,
            cpf,
            createdAt,
            updatedAt,
            deletedAt
        );
    }

    public static Client from(final Client client) {
        return new Client(
                client.getId(),
                client.publicId,
                client.name,
                client.email,
                client.cpf,
                client.getCreatedAt(),
                client.getUpdatedAt(),
                client.getDeletedAt()
        );
    }

    public Client update(
            final UUID publicId,
            final String name,
            final String email,
            final ClientCpf cpf
    ) {
        this.publicId = publicId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.updatedAt = InstantUtils.now();
        this.selfValidation();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new ClientValidator(this, handler).validate();
    }

    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create client", notification);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ClientCpf getCpf() {
        return cpf;
    }

    public UUID getPublicId() {
        return publicId;
    }
}
