package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, Integer> {
}
