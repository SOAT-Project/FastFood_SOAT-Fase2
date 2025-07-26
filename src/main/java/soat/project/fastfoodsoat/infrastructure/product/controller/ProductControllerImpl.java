package soat.project.fastfoodsoat.infrastructure.product.controller;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.product.create.CreateProductCommand;
import soat.project.fastfoodsoat.application.command.product.retrieve.list.bycategory.ListByCategoryCommand;
import soat.project.fastfoodsoat.application.command.product.update.UpdateProductCommand;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.delete.DeleteProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.bycategory.ListByCategoryUseCase;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductUseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.infrastructure.product.model.request.CreateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.request.UpdateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.response.CreateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.GetProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.UpdateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.presenter.ProductPresenter;

@Component
public class ProductControllerImpl implements ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ListByCategoryUseCase listByCategoryUseCase;

    public ProductControllerImpl(
            final CreateProductUseCase createProductUseCase,
            final GetProductUseCase getProductUseCase,
            final UpdateProductUseCase updateProductUseCase,
            final DeleteProductUseCase deleteProductUseCase,
            final ListByCategoryUseCase listByCategoryUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.listByCategoryUseCase = listByCategoryUseCase;
    }

    @Override
    public CreateProductResponse create(final CreateProductRequest request) {
        var command = new CreateProductCommand(
                request.name(),
                request.description(),
                request.value(),
                request.imageUrl(),
                request.productCategoryId()
        );
        var output = createProductUseCase.execute(command);
        return ProductPresenter.present(output);
    }

    @Override
    public GetProductResponse getById(final Integer id) {
        final var output = getProductUseCase.execute(id);
        return ProductPresenter.present(output);
    }

    @Override
    public UpdateProductResponse update(final Integer id, final UpdateProductRequest request) {
        var command = new UpdateProductCommand(
                id,
                request.name(),
                request.description(),
                request.value(),
                request.imageUrl(),
                request.productCategoryId()
        );
        var output = updateProductUseCase.execute(command);
        return ProductPresenter.present(output);
    }

    @Override
    public void delete(final Integer id) {
        this.deleteProductUseCase.execute(id);
    }

    @Override
    public Pagination<ListProductByCategoryResponse> listByCategory(
            final Integer categoryId,
            final int page,
            final int perPage,
            final String sort,
            final String dir,
            final String search
    ) {
        var query = new SearchQuery(page, perPage, search, sort, dir);
        var params = ListByCategoryCommand.with(categoryId, query);
        var result = listByCategoryUseCase.execute(params);

        return new Pagination<>(
                result.currentPage(),
                result.perPage(),
                result.total(),
                result.items().stream()
                        .map(ProductPresenter::present)
                        .toList()
        );
    }
}
