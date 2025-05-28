package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderJpaEntity, Integer> {

    @Query("SELECT COALESCE(MAX(o.orderNumber), 0) FROM OrderJpaEntity o")
    Optional<Integer> findMaxOrderNumber();

    @Query("SELECT o FROM OrderJpaEntity o WHERE o.publicId = :publicId AND o.deletedAt IS NULL")
    Optional<OrderJpaEntity> findOneByPublicId(UUID publicId);

    Page<OrderJpaEntity> findByPublicId(UUID publicId, Pageable pageable);
    Page<OrderJpaEntity> findByOrderNumber(Integer orderNumber, Pageable pageable);
    Page<OrderJpaEntity> findByStatus(String status, Pageable pageable);
    Page<OrderJpaEntity> findDistinctByOrderProductsProductNameContainingIgnoreCase(String productName, Pageable pageable);

}
