package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.ProductCategoryMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryGateway;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.Optional;

@Component
public class ProductCategoryJpaGateway implements ProductCategoryGateway {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryJpaGateway(final ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {
        return null;
    }

    @Override
    public void deleteById(ProductCategoryId productCategoryId) {

    }

    @Override
    public Optional<ProductCategory> findById(ProductCategoryId productCategoryId) {
        return productCategoryRepository
                .findById(productCategoryId.getValue())
                .map(ProductCategoryMapper::toDomain);
    }
}
