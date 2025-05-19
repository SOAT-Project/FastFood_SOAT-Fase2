package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.PaymentJpaEntity;

public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, Integer>  {
}
