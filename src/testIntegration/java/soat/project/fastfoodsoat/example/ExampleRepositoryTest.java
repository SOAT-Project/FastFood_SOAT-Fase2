package soat.project.fastfoodsoat.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ExampleEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ExampleRepository;
import soat.project.fastfoodsoat.setup.BaseIntegrationTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration-test")
public class ExampleRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private ExampleRepository exampleRepository;

    @Test
    void deveSalvarEBuscarEmpresa() {
        ExampleEntity example = new ExampleEntity("OpenAI", "12345678000199");
        exampleRepository.save(example);

        Optional<ExampleEntity> encontrada = exampleRepository.findById(example.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getName()).isEqualTo("12345678000199");
    }
}
