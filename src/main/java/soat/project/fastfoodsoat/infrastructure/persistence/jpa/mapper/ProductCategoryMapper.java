package soat.project.fastfoodsoat.infrastructure.persistence.jpa.mapper;

import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;

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

    public static ProductCategoryJpaEntity fromDomain(ProductCategory category) {
        return new ProductCategoryJpaEntity(
                category.getId() != null ? category.getId().getValue() : null,
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }
}
