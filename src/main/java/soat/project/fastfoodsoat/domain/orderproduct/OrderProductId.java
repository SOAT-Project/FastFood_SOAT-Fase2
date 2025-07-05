package soat.project.fastfoodsoat.domain.orderproduct;

import soat.project.fastfoodsoat.domain.shared.Identifier;

import java.util.Objects;

public class OrderProductId extends Identifier {

    private final Integer id;

    private OrderProductId(final Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public static OrderProductId of(final Integer id) {
        return new OrderProductId(id);
    }

    @Override
    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final OrderProductId orderId = (OrderProductId) o;
        return Objects.equals(getValue(), orderId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
