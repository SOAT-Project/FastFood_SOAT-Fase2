package soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductJpaEntity;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, Integer>, JpaSpecificationExecutor<ProductJpaEntity> {
}
