package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.application.command.product.create.CreateProductCommand;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class CreateProductUseCaseIT {

    @Autowired
    private CreateProductUseCaseImpl useCase;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void individualTestSetup() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

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
    void givenValidCommand_whenCreateProduct_thenShouldReturnProductId() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var name = "X-Burger";
        final var description = "podrão de quiejo";
        final var price = BigDecimal.valueOf(19.99);
        final var image = "burger.jpg";
        final var categoryId = category.getId();

        final var command = new CreateProductCommand(name, description, price, image, categoryId);

        final var output = useCase.execute(command);

        assertNotNull(output);
        assertNotNull(output.id());
    }

    @Test
    void givenInvalidProductData_whenCreateProduct_thenShouldThrowNotificationException() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var command = new CreateProductCommand("", "", null, null, category.getId());

        final var ex = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals("could not create product", ex.getMessage());
        assertFalse(ex.getErrors().isEmpty());
    }

    @Test
    void givenInvalidCategoryId_whenCreateProduct_thenShouldThrowNotFoundException() {
        final var invalidCategoryId = 999;
        final var command = new CreateProductCommand("lanchao", "lanche zoado", BigDecimal.valueOf(99), "lanchao.jpg", invalidCategoryId);

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        assertEquals("productcategory with id 999 was not found", exception.getMessage());

        assertFalse(exception.getErrors().isEmpty());
    }

}