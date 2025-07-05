package soat.project.fastfoodsoat.infrastructure.persistence;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.mapper.ProductCategoryMapper;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategory;
import soat.project.fastfoodsoat.application.gateway.ProductCategoryRepositoryGateway;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;

import java.util.Optional;

@Component
public class ProductCategoryRepositoryGatewayImpl implements ProductCategoryRepositoryGateway {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryRepositoryGatewayImpl(final ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {
        return null;
    }

    @Override
    public Optional<ProductCategory> findById(ProductCategoryId productCategoryId) {
        return productCategoryRepository
                .findById(productCategoryId.getValue())
                .map(ProductCategoryMapper::toDomain);
    }
}
