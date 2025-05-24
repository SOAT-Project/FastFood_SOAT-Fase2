package soat.project.fastfoodsoat.domain.client;

import soat.project.fastfoodsoat.domain.Identifier;

import java.util.Objects;

public class ClientId extends Identifier {

    private final Integer id;

    private ClientId(final Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public static ClientId of(final Integer id) {
        return new ClientId(id);
    }

    @Override
    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ClientId clientId = (ClientId) o;
        return Objects.equals(getValue(), clientId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
