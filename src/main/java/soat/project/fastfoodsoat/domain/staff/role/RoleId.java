package soat.project.fastfoodsoat.domain.staff.role;

import soat.project.fastfoodsoat.domain.Identifier;

import java.util.Objects;

public class RoleId extends Identifier {

    private final Integer id;

    private RoleId(final Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public static RoleId of(final Integer id) {
        return new RoleId(id);
    }

    @Override
    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final RoleId roleId = (RoleId) o;
        return Objects.equals(getValue(), roleId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
