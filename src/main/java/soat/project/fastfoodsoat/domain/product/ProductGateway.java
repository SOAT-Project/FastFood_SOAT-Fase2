package soat.project.fastfoodsoat.domain.product;

import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.Optional;

public interface ProductGateway {
    Product create(Product product);
    Product update(Product product);
    void deleteById(ProductId productId);
    Optional<Product> findById(ProductId productId);
    Pagination<Product> findProductByCategory(ProductCategoryId productCategoryId, SearchQuery searchQuery);
    Pagination<Product> findAll(SearchQuery searchQuery);
}
