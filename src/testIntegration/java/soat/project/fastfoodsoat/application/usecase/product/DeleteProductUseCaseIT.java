package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.application.usecase.product.delete.DefaultDeleteProductUseCase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@IntegrationTest
class DeleteProductUseCaseIT {

    @Autowired
    private DefaultDeleteProductUseCase useCase;

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
    void givenValidId_whenDeleteProduct_thenShouldDeleteSuccessfully() {
        final var productId = 123;

        assertDoesNotThrow(() -> useCase.execute(productId));
    }

}
