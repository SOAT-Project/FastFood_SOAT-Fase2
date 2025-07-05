package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductCategoryJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class GetProductUseCaseIT {

    @Autowired
    private GetProductUseCaseImpl useCase;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

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
    void givenValidId_whenGetProduct_thenShouldReturnProduct() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var name = "X-Burger";
        final var description = "podrão de quiejo";
        final var price = BigDecimal.valueOf(19.99);
        final var image = "burger.jpg";
        final var categoryId = ProductCategoryId.of(category.getId());

        final var product = productRepository.save(new ProductJpaEntity(null, name, description, price, image, categoryId, Instant.now(), Instant.now(), null));
        productRepository.save(product);

        final var id = product.getId();

        final var output = useCase.execute(id);

        assertNotNull(output);
    }

    @Test
    void givenInvalidId_whenGetProduct_thenShouldThrowNotFoundException() {
        final var id = 999;

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(id));
        assertTrue(exception.getMessage().contains("product with id"));
    }

}
