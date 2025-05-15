package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.StaffJpaEntity;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffJpaEntity, Integer> {
    Optional<StaffJpaEntity> findByEmail(String email);
    Optional<StaffJpaEntity> findByCpf(String cpf);
}