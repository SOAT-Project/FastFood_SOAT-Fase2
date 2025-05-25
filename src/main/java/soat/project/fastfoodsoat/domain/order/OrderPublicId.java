package soat.project.fastfoodsoat.domain.order;

import soat.project.fastfoodsoat.domain.PublicIdentifier;

import java.util.Objects;
import java.util.UUID;

public class OrderPublicId extends PublicIdentifier {

    private final UUID publicId;

    private OrderPublicId(final UUID publicId) {
        this.publicId = Objects.requireNonNull(publicId);
    }

    public static OrderPublicId of(final UUID publicId) {
        return new OrderPublicId(publicId);
    }

    @Override
    public UUID getValue() {
        return publicId;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final OrderPublicId orderId = (OrderPublicId) o;
        return Objects.equals(getValue(), orderId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
