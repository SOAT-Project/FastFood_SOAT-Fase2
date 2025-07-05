package soat.project.fastfoodsoat.application.usecase.product;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory.ListByCategoryUseCaseImpl;
import soat.project.fastfoodsoat.application.output.product.ListByCategoryOutput;
import soat.project.fastfoodsoat.application.command.product.retrieve.list.bycategory.ListByCategoryCommand;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategory;
import soat.project.fastfoodsoat.application.gateway.ProductCategoryRepositoryGateway;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListByCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ListByCategoryUseCaseImpl useCase;

    @Mock
    private ProductRepositoryGateway productRepositoryGateway;

    @Mock
    private ProductCategoryRepositoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productRepositoryGateway, categoryGateway);
    }

    @Test
    void givenValidCategory_whenListProducts_thenShouldReturnPagination() {
        final var categoryId = 10;
        final var now = Instant.now();
        final var category = ProductCategory.with(ProductCategoryId.of(categoryId), "Lanches", now, now, null);
        final var query = mock(SearchQuery.class);
        final var params = new ListByCategoryCommand(categoryId, query);


        final var expectedPagination = Pagination.with(0, 10, 1, List.of(
                new ListByCategoryOutput(1, "pastel", "pastelaço", BigDecimal.valueOf(10), "img.png", categoryId, now, now)
        ));

        final var pagination = mock(Pagination.class);

        when(categoryGateway.findById(ProductCategoryId.of(categoryId))).thenReturn(Optional.of(category));
        when(productRepositoryGateway.findProductByCategory(ProductCategoryId.of(categoryId), query)).thenReturn(pagination);
        when(pagination.map(any())).thenReturn(expectedPagination);

        final var result = useCase.execute(params);

        assertNotNull(result);
        verify(productRepositoryGateway).findProductByCategory(ProductCategoryId.of(categoryId), query);
    }

    @Test
    void givenInvalidCategoryId_whenListProducts_thenShouldThrowNotFound() {
        final var categoryId = 99;
        final var query = mock(SearchQuery.class);
        final var params = new ListByCategoryCommand(categoryId, query);

        when(categoryGateway.findById(ProductCategoryId.of(categoryId))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> useCase.execute(params));
        verify(productRepositoryGateway, never()).findProductByCategory(any(), any());
    }

    @Test
    void givenCategoryWithNoProducts_whenListProducts_thenShouldReturnEmptyPagination() {
        final var categoryId = 5;
        final var category = ProductCategory.with(ProductCategoryId.of(categoryId), "Bebidas", Instant.now(), Instant.now(), null);
        final var query = mock(SearchQuery.class);
        final var params = new ListByCategoryCommand(categoryId, query);
        final Pagination emptyPagination = Pagination.with(0, 1, 10, List.of());

        when(categoryGateway.findById(ProductCategoryId.of(categoryId))).thenReturn(Optional.of(category));
        when(productRepositoryGateway.findProductByCategory(ProductCategoryId.of(categoryId), query)).thenReturn(emptyPagination);

        final var result = useCase.execute(params);

        assertNotNull(result);
        assertTrue(result.items().isEmpty());
    }


}
