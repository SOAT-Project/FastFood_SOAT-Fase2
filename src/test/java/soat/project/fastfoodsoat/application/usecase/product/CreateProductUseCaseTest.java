package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductCommand;
import soat.project.fastfoodsoat.application.usecase.product.create.DefaultCreateProductUseCase;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryGateway;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private ProductCategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway, categoryGateway);
    }

    @Test
    void givenValidCommand_whenCreateProduct_thenShouldReturnProductId() {

        final var name = "X-Burger";
        final var description = "podrão de quiejo";
        final var price = BigDecimal.valueOf(19.99);
        final var image = "burger.jpg";
        final var categoryId = 10;
        final var now = Instant.now();

        final var command = new CreateProductCommand(name, description, price, image, categoryId);
        final var category = ProductCategory.with(ProductCategoryId.of(categoryId), "Lanches", now, now, null);

        when(categoryGateway.findById(ProductCategoryId.of(categoryId))).thenReturn(Optional.of(category));
        when(productGateway.create(any())).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            ReflectionTestUtils.setField(p, "id", ProductId.of(1));
            return p;
        });


        final var output = useCase.execute(command);

        assertNotNull(output);
        assertEquals(1, output.id());
        verify(categoryGateway, times(1)).findById(ProductCategoryId.of(categoryId));
        verify(productGateway, times(1)).create(any());
    }

    @Test
    void givenInvalidProductData_whenCreateProduct_thenShouldThrowNotificationException() {
        final var command = new CreateProductCommand("", "", null, null, 10);
        final var now = Instant.now();
        final var category = ProductCategory.with(ProductCategoryId.of(10), "Lanches", now, now, null);

        when(categoryGateway.findById(ProductCategoryId.of(10))).thenReturn(Optional.of(category));

        final var ex = assertThrows(NotificationException.class, () -> useCase.execute(command));

        assertEquals("could not create product", ex.getMessage());
        assertFalse(ex.getErrors().isEmpty());
        verify(productGateway, never()).create(any());
    }


    @Test
    void givenInvalidCategoryId_whenCreateProduct_thenShouldThrowNotFoundException() {

        final var command = new CreateProductCommand("lanchao", "lanche zoado", BigDecimal.valueOf(99), "lanchao.jpg", 999);
        final var categoryId = ProductCategoryId.of(999);

        when(categoryGateway.findById(categoryId)).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        assertEquals("productcategory with id 999 was not found", exception.getMessage());

        assertTrue(exception.getErrors().isEmpty());

        verify(categoryGateway, times(1)).findById(categoryId);
        verify(productGateway, never()).create(any());
    }
}