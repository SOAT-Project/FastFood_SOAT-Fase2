package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

public final class ProductCategoryMapper {

    public static ProductCategory toDomain(ProductCategoryJpaEntity categoryJpa) {
        return ProductCategory.with(
                ProductCategoryId.of(categoryJpa.getId()),
                categoryJpa.getName(),
                categoryJpa.getCreatedAt(),
                categoryJpa.getUpdatedAt(),
                categoryJpa.getDeletedAt()
        );
    }
}
