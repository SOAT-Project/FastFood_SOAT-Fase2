package soat.project.fastfoodsoat.domain.staff;

import soat.project.fastfoodsoat.domain.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.staff.role.Role;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;
import soat.project.fastfoodsoat.utils.InstantUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Staff extends AggregateRoot<StaffId> {

    private String name;
    private String email;
    private StaffCpf cpf;
    private boolean isActive;
    private final List<Role> roles = new ArrayList<>();

    private Staff(
            final StaffId id,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt,
            final String name,
            final String email,
            final StaffCpf cpf,
            final boolean isActive,
            final List<Role> roles
    ) {
        super(
                id,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.isActive = isActive;
        this.roles.addAll(roles);
        this.selfValidation();
    }

    public static Staff newStaff(
            final String name,
            final String email,
            final String cpf,
            final List<Role> roles
    ) {
        final StaffId staffId = null; // O valor do ID será na camada de persistência
        final StaffCpf staffCpf = StaffCpf.of(cpf);
        final boolean isActive = true; // O Staff sempre será criado como ativo
        final Instant now = InstantUtils.now();
        return new Staff(
                staffId,
                now,
                now,
                null,
                name,
                email,
                staffCpf,
                isActive,
                roles
        );
    }

    public static Staff with(
            final StaffId id,
            final String name,
            final String email,
            final StaffCpf cpf,
            final List<Role> roles,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Staff(
                id,
                createdAt,
                updatedAt,
                deletedAt,
                name,
                email,
                cpf,
                isActive,
                roles
        );
    }

    public static Staff from(final Staff staff) {
        return new Staff(
                staff.id,
                staff.createdAt,
                staff.updatedAt,
                staff.deletedAt,
                staff.name,
                staff.email,
                staff.cpf,
                staff.isActive,
                staff.roles
        );
    }

    public Staff update(
            final String name,
            final String email,
            final StaffCpf cpf
    ) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.updatedAt = InstantUtils.now();
        this.selfValidation();
        return this;
    }

    public Staff activate() {
        if (this.isActive) return this;

        this.isActive = true;
        this.updatedAt = InstantUtils.now();
        this.deletedAt = null;
        return this;
    }

    public Staff deactivate() {
        if (!this.isActive) return this;

        this.isActive = false;
        this.updatedAt = InstantUtils.now();
        this.deletedAt = InstantUtils.now();
        return this;
    }

    public Staff addRole(final Role role) {
        if (this.roles.contains(role)) return this;

        this.roles.add(role);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Staff removeRole(final Role role) {
        if (!this.roles.contains(role)) return this;

        this.roles.remove(role);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new StaffValidator(this, handler).validate();
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

    public String getEmail() {
        return email;
    }

    public StaffCpf getCpf() {
        return cpf;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<Role> getRoles() {
        return roles;
    }

}
