package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.DefaultGetProductUseCase;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenGetProduct_thenShouldReturnProduct() {
        final var id = 1;
        final var productId = ProductId.of(id);
        final var categoryId = ProductCategoryId.of(1);
        final var product = mock(Product.class);

        when(product.getId()).thenReturn(productId);
        when(product.getProductCategoryId()).thenReturn(categoryId);
        when(productGateway.findById(productId)).thenReturn(Optional.of(product));

        final var output = useCase.execute(id);

        assertNotNull(output);
        verify(productGateway, times(1)).findById(productId);
    }

    @Test
    void givenInvalidId_whenGetProduct_thenShouldThrowNotFoundException() {
        final var id = 999;
        when(productGateway.findById(ProductId.of(id))).thenReturn(Optional.empty());

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(id));
        assertTrue(exception.getMessage().contains("product with id"));
    }
}
