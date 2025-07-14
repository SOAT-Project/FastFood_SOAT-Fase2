package soat.project.fastfoodsoat.domain.product;

import soat.project.fastfoodsoat.domain.shared.Identifier;

import java.util.Objects;

public class ProductId extends Identifier {

    private final Integer id;

    public ProductId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getValue() {
        return id;
    }

    public static ProductId of(final Integer id) {
        return new ProductId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(id, productId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
