package soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.RoleJpaEntity;

public interface RoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
}