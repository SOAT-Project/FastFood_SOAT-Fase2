package soat.project.fastfoodsoat.domain.client;

import soat.project.fastfoodsoat.domain.PublicIdentifier;

import java.util.Objects;
import java.util.UUID;

public class ClientPublicId extends PublicIdentifier {

    private final UUID publicId;

    private ClientPublicId(final UUID publicId) {
        this.publicId = Objects.requireNonNull(publicId);
    }

    public static ClientPublicId of(final UUID publicId) {
        return new ClientPublicId(publicId);
    }

    @Override
    public UUID getValue() {
        return publicId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ClientPublicId that = (ClientPublicId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}