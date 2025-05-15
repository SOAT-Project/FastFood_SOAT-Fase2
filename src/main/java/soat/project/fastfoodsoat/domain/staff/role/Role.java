package soat.project.fastfoodsoat.domain.staff.role;

import soat.project.fastfoodsoat.domain.Entity;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.time.Instant;

public class Role extends Entity<RoleId> {

    private String name;

    public Role(
            final RoleId id,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt,
            final String name
    ) {
        super(
                id,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.name = name;
        this.selfValidation();
    }

    public static Role newRole(
            final String name
    ) {
        final RoleId roleId = null; // O valor do ID será na camada de persistência
        final Instant now = Instant.now();
        return new Role(
                roleId,
                now,
                now,
                null,
                name
        );
    }

    public static Role with(
            final RoleId id,
            final String name,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Role(
                id,
                createdAt,
                updatedAt,
                deletedAt,
                name
        );
    }

    public static Role with(final Role role) {
        return with(
                role.getId(),
                role.name,
                role.getCreatedAt(),
                role.getUpdatedAt(),
                role.getDeletedAt()
        );
    }

    public Role update(
            final String name
    ) {
        this.name = name;
        this.updatedAt = Instant.now();
        this.selfValidation();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new RoleValidator(this, handler).validate();
    }


    private void selfValidation() {
        final var notification = Notification.create();

        this.validate(notification);

        if (notification.hasError())
            throw new NotificationException("failed to create staff", notification);
    }

    public String getName() {
        return name;
    }

}
