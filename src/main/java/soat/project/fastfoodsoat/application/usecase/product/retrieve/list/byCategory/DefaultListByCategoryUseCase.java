package soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory;

import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.product.ProductGateway;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategory;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryGateway;
import soat.project.fastfoodsoat.domain.product.productCategory.ProductCategoryId;

import java.util.function.Supplier;

public class DefaultListByCategoryUseCase extends ListByCategoryUseCase {

    private final ProductGateway productGateway;
    private final ProductCategoryGateway productCategoryGateway;

    public DefaultListByCategoryUseCase(
            final ProductGateway productGateway,
            final ProductCategoryGateway productCategoryGateway
    ) {
        this.productGateway = productGateway;
        this.productCategoryGateway = productCategoryGateway;
    }

    @Override
    public Pagination<ListByCategoryOutput> execute(final ListByCategoryParams params) {
        final var productCategoryId = ProductCategoryId.of(params.productCategoryId());
        final var query = params.searchQuery();

        final var category = productCategoryGateway
                .findById(productCategoryId)
                .orElseThrow(categoryNotFound(productCategoryId));

        return this.productGateway
                .findProductByCategory(category.getId(), query)
                .map(ListByCategoryOutput::from);
    }

    private Supplier<NotFoundException> categoryNotFound(final ProductCategoryId id) {
        return () -> NotFoundException.with(ProductCategory.class, id);
    }
}
