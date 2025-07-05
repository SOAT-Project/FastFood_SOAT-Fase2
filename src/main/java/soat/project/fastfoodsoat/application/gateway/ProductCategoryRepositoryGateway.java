package soat.project.fastfoodsoat.application.gateway;

import soat.project.fastfoodsoat.domain.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;

import java.util.Optional;

public interface ProductCategoryRepositoryGateway {
    ProductCategory create(ProductCategory productCategory);
    Optional<ProductCategory> findById(ProductCategoryId productCategoryId);
}
