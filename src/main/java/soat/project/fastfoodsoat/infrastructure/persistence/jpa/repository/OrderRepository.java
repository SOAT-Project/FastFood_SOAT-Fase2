package soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

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

    Page<OrderJpaEntity> findAllByPayment_Status(PaymentStatus paymentStatus, Pageable pageable);
    Page<OrderJpaEntity> findAllByPublicIdAndPayment_Status(UUID publicId, PaymentStatus status, Pageable pageable);
    Page<OrderJpaEntity> findAllByOrderNumberAndPayment_Status(Integer orderNumber, PaymentStatus status, Pageable pageable);
    Page<OrderJpaEntity> findAllByOrderProducts_ProductNameContainingIgnoreCaseAndPayment_Status(
            String productName, PaymentStatus status, Pageable pageable
    );

    @Query("SELECT o FROM OrderJpaEntity o JOIN PaymentJpaEntity p ON p.order = o WHERE o.status <> 'COMPLETED' AND p.status = 'APPROVED' ORDER BY CASE o.status WHEN 'READY' THEN 1 WHEN 'IN_PREPARATION' THEN 2 WHEN 'RECEIVED' THEN 3 ELSE 4 END, o.createdAt ASC")
    Page<OrderJpaEntity> findAllExcludingFinalizedAndSorted(Pageable pageable);
}
