package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductCategoryJpaEntity;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryJpaEntity, Integer> {
}
