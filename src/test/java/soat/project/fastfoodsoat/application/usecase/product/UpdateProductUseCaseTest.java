package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.product.update.DefaultUpdateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductCommand;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.ProductId;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidCommand_whenUpdateProduct_thenShouldReturnUpdatedProduct() {
        final var id = 1;
        final var productCategoryId = 10;
        final var product = mock(Product.class);
        final var command = new UpdateProductCommand(id, "Updated", "Description", BigDecimal.TEN, "img.png", productCategoryId);

        when(productGateway.findById(ProductId.of(id))).thenReturn(Optional.of(product));
        when(productGateway.update(product)).thenReturn(product);

        final var output = useCase.execute(command);

        assertNotNull(output);
        verify(product).update("Updated", "Description", BigDecimal.TEN, "img.png", ProductCategoryId.of(productCategoryId));
        verify(productGateway).update(product);
    }

    @Test
    void givenInvalidId_whenUpdateProduct_thenShouldThrowNotFound() {
        final var command = new UpdateProductCommand(999, "Updated", "Description", BigDecimal.TEN, "img.png", 10);

        when(productGateway.findById(ProductId.of(999))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
    }
}
