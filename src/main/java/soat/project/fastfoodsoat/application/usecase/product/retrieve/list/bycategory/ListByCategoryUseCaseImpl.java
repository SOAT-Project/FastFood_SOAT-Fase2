package soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.product.retrieve.list.bycategory.ListByCategoryCommand;
import soat.project.fastfoodsoat.application.output.product.ListByCategoryOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.application.gateway.ProductRepositoryGateway;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategory;
import soat.project.fastfoodsoat.application.gateway.ProductCategoryRepositoryGateway;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.util.function.Supplier;

@Component
public class ListByCategoryUseCaseImpl extends ListByCategoryUseCase {

    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductCategoryRepositoryGateway productCategoryRepositoryGateway;

    public ListByCategoryUseCaseImpl(
            final ProductRepositoryGateway productRepositoryGateway,
            final ProductCategoryRepositoryGateway productCategoryRepositoryGateway
    ) {
        this.productRepositoryGateway = productRepositoryGateway;
        this.productCategoryRepositoryGateway = productCategoryRepositoryGateway;
    }

    @Override
    public Pagination<ListByCategoryOutput> execute(final ListByCategoryCommand params) {
        final var productCategoryId = ProductCategoryId.of(params.productCategoryId());
        final var query = params.searchQuery();

        final var category = productCategoryRepositoryGateway
                .findById(productCategoryId)
                .orElseThrow(categoryNotFound(productCategoryId));

        return this.productRepositoryGateway
                .findProductByCategory(category.getId(), query)
                .map(ListByCategoryOutput::from);
    }

    private Supplier<NotFoundException> categoryNotFound(final ProductCategoryId id) {
        return () -> NotFoundException.with(ProductCategory.class, id);
    }
}
