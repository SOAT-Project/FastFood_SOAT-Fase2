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
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductCategoryRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory.ListByCategoryUseCaseImpl;
import soat.project.fastfoodsoat.application.command.product.ListByCategoryParams;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.domain.productCategory.ProductCategoryId;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class ListByCategoryUseIT {

    @Autowired
    private ListByCategoryUseCaseImpl useCase;

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

    @BeforeEach
    public void cleanup() {
        createProduct1();
        createProduct2();
    }

    private void createProduct1() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var name = "X-Burger";
        final var description = "podrão de quiejo";
        final var price = BigDecimal.valueOf(19.99);
        final var image = "burger.jpg";
        final var categoryId = ProductCategoryId.of(category.getId());

        final var product = productRepository.save(new ProductJpaEntity(null, name, description, price, image, categoryId, Instant.now(), Instant.now(), null));
        productRepository.save(product);
    }

    private void createProduct2() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Bebidas", Instant.now(), Instant.now(), null));

        final var name = "Bebidas";
        final var description = "Coca Cola";
        final var price = BigDecimal.valueOf(09.99);
        final var image = "burger.jpg";
        final var categoryId = ProductCategoryId.of(category.getId());

        final var product = productRepository.save(new ProductJpaEntity(null, name, description, price, image, categoryId, Instant.now(), Instant.now(), null));
        productRepository.save(product);
    }

    @Test
    void givenValidCategory_whenListProducts_thenShouldReturnPagination() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var categoryId = category.getId();
        final var query = new SearchQuery(0,10,"", "name", "asc");
        final var params = new ListByCategoryParams(categoryId, query);

        final var result = useCase.execute(params);

        assertNotNull(result);
    }

    @Test
    void givenInvalidCategoryId_whenListProducts_thenShouldThrowNotFound() {
        final var categoryId = 10;
        final var query = new SearchQuery(0,10,"", "name", "asc");
        final var params = new ListByCategoryParams(categoryId, query);

        assertThrows(NotFoundException.class, () -> useCase.execute(params));
    }

    @Test
    void givenCategoryWithNoProducts_whenListProducts_thenShouldReturnEmptyPagination() {
        final var category = productCategoryRepository.save(new ProductCategoryJpaEntity(null, "Lanches", Instant.now(), Instant.now(), null));

        final var categoryId = category.getId();
        final var query = new SearchQuery(0,10,"", "name", "asc");
        final var params = new ListByCategoryParams(categoryId, query);

        final var result = useCase.execute(params);

        assertNotNull(result);
        assertTrue(result.items().isEmpty());
    }

}
