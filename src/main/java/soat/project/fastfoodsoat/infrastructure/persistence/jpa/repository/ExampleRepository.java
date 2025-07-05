package soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ExampleEntity;

public interface ExampleRepository extends JpaRepository<ExampleEntity, String> {;
}
