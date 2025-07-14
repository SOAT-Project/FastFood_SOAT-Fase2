package soat.project.fastfoodsoat.domain.client;

import soat.project.fastfoodsoat.domain.shared.ValueObject;

import java.util.Objects;

public class ClientCpf  extends ValueObject {

    private final String cpf;

    private ClientCpf(final String cpf) {
        this.cpf = cpf;
    }

    public static ClientCpf of(final String cpf) {
        if (Objects.isNull(cpf)) return new ClientCpf(null);

        final String cpfWithoutMask = removeMask(cpf);
        return new ClientCpf(cpfWithoutMask);
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
        final ClientCpf clientCpf = (ClientCpf) o;
        return Objects.equals(cpf, clientCpf.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}

