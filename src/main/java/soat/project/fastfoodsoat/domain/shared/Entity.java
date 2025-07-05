package soat.project.fastfoodsoat.domain.shared;

import soat.project.fastfoodsoat.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    protected final Instant createdAt;
    protected Instant updatedAt;
    protected Instant deletedAt;

    protected Entity(
            final ID id,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
