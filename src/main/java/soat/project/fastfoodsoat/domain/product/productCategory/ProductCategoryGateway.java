package soat.project.fastfoodsoat.domain.product.productCategory;

import java.util.Optional;

public interface ProductCategoryGateway {
    ProductCategory create(ProductCategory productCategory);
    void deleteById(ProductCategoryId productCategoryId);
    Optional<ProductCategory> findById(ProductCategoryId productCategoryId);


}
