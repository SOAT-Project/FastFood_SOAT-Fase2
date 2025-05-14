package soat.project.fastfoodsoat.domain.product.productCategory;

import soat.project.fastfoodsoat.domain.Identifier;

import java.util.Objects;

public class ProductCategoryId extends Identifier {

    private final Integer id;

    public ProductCategoryId(Integer id) {
        this.id = id;
    }


    @Override
    public Integer getValue() {
        return id;
    }

    public static ProductCategoryId of (final Integer id){
        return new ProductCategoryId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryId that = (ProductCategoryId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
