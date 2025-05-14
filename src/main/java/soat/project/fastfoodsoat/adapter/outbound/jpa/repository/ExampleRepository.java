package soat.project.fastfoodsoat.adapter.outbound.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ExampleEntity;

public interface ExampleRepository extends JpaRepository<ExampleEntity, String> {;
}
