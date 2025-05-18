package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientJpaEntity, Integer> {
    Optional<ClientJpaEntity> findByCpf(String cpf);
}
