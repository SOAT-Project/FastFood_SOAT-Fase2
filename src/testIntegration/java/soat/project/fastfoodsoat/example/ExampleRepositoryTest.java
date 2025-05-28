package soat.project.fastfoodsoat.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ExampleEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ExampleRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class ExampleRepositoryTest {

    @Autowired
    private ExampleRepository exampleRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void deveSalvarEBuscarEmpresa() {
        ExampleEntity example = new ExampleEntity("OpenAI", "12345678000199");
        exampleRepository.save(example);

        Optional<ExampleEntity> encontrada = exampleRepository.findById(example.getId());

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getName()).isEqualTo("12345678000199");
    }
}
