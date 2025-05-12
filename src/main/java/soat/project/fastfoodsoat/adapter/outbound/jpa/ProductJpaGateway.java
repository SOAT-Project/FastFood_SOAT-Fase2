package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.Optional;

@Component
public class ProductJpaGateway implements ProductGateway {

    private final ProductRepository productRepository;

    public ProductJpaGateway(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(final Product product) {
        return save(product);
    }

    @Override
    public Product update(final Product product) {
        return null;
    }

    @Override
    public void deleteById(final ProductId productId) {

    }

    @Override
    public Optional<Product> findById(final ProductId productId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Product> findProductByCategory(final ProductCategoryId productCategoryId, final SearchQuery searchQuery) {
        return null;
    }

    @Override
    public Pagination<Product> findAll(final SearchQuery searchQuery) {
        return null;
    }

    private Product save(final Product product){
        return this.productRepository.save(ProductJpaEntity.fromDomain(product)).toDomain();
    }
}
