package soat.project.fastfoodsoat.domain.staff;

import soat.project.fastfoodsoat.domain.ValueObject;

import java.util.Objects;

public class StaffCpf extends ValueObject {

    private final String cpf;

    private StaffCpf(final String cpf) {
        this.cpf = cpf;
    }

    public static StaffCpf of(final String cpf) {
        if (Objects.isNull(cpf)) return new StaffCpf(null);

        final String cpfWithoutMask = removeMask(cpf);
        return new StaffCpf(cpfWithoutMask);
    }

    public static String removeMask(final String cpf) {
        Objects.requireNonNull(cpf);
        return cpf.replaceAll("[.-]", "").trim();
    }

    public String getValue() {
        return cpf;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final StaffCpf staffCpf = (StaffCpf) o;
        return Objects.equals(cpf, staffCpf.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}
