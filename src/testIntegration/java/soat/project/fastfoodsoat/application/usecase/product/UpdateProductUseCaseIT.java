package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.application.usecase.product.update.DefaultUpdateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductCommand;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class UpdateProductUseCaseIT {

    @Autowired
    private DefaultUpdateProductUseCase useCase;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

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
    void givenValidCommand_whenUpdateProduct_thenShouldReturnUpdatedProduct() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var name = "X-Burger";
        final var description = "podrão de quiejo";
        final var price = BigDecimal.valueOf(19.99);
        final var image = "burger.jpg";
        final var categoryId = ProductCategoryId.of(category.getId());

        final var productJpa = productRepository.save(new ProductJpaEntity(null, name, description, price, image, categoryId, Instant.now(), Instant.now(), null));
        productRepository.save(productJpa);

        final var id = 1;
        final var productCategoryId = category.getId();
        final var command = new UpdateProductCommand(id, "Updated", "Description", BigDecimal.TEN, "img.png", productCategoryId);

        final var output = useCase.execute(command);

        assertNotNull(output);
    }

    @Test
    void givenInvalidId_whenUpdateProduct_thenShouldThrowNotFound() {
        final var command = new UpdateProductCommand(999, "Updated", "Description", BigDecimal.TEN, "img.png", 10);

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
    }
}
