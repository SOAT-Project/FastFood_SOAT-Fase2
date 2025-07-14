package soat.project.fastfoodsoat.domain.staff;

import soat.project.fastfoodsoat.domain.shared.Identifier;

import java.util.Objects;

public class StaffId extends Identifier {

    private final Integer id;

    private StaffId(final Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public static StaffId of(final Integer id) {
        return new StaffId(id);
    }

    @Override
    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final StaffId staffId = (StaffId) o;
        return Objects.equals(getValue(), staffId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
