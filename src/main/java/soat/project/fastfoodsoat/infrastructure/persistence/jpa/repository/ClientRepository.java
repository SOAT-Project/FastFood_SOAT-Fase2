package soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ClientJpaEntity;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<ClientJpaEntity, Integer> {
    Optional<ClientJpaEntity> findByCpf(String cpf);

    Optional<ClientJpaEntity> findByPublicId(UUID publicId);

}
