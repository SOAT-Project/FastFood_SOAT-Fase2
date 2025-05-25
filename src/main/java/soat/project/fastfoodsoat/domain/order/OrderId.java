package soat.project.fastfoodsoat.domain.order;

import soat.project.fastfoodsoat.domain.Identifier;

import java.util.Objects;

public class OrderId extends Identifier {

    private final Integer id;

    private OrderId(final Integer id) {
        this.id = Objects.requireNonNull(id);
    }

    public static OrderId of(final Integer id) {
        return new OrderId(id);
    }

    @Override
    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final OrderId orderId = (OrderId) o;
        return Objects.equals(getValue(), orderId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

}
