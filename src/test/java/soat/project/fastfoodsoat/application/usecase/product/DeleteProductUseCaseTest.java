package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.product.delete.DefaultDeleteProductUseCase;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenDeleteProduct_thenShouldDeleteSuccessfully() {
        final var productId = 123;

        assertDoesNotThrow(() -> useCase.execute(productId));
        verify(productGateway, times(1)).deleteById(ProductId.of(productId));
    }
}
