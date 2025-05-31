package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.adapter.inbound.api.ProductAPI;
import soat.project.fastfoodsoat.adapter.inbound.api.model.*;
import soat.project.fastfoodsoat.adapter.inbound.api.presenter.ProductPresenter;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductCommand;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.delete.DeleteProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductUseCase;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory.ListByCategoryParams;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory.ListByCategoryUseCase;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductCommand;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductUseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

@RestController
public class ProductController implements ProductAPI {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ListByCategoryUseCase listByCategoryUseCase;

    public ProductController(
            final CreateProductUseCase createProductUseCase,
            final GetProductUseCase getProductUseCase,
            final UpdateProductUseCase updateProductUseCase,
            final DeleteProductUseCase deleteProductUseCase,
            final ListByCategoryUseCase listByCategoryUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.listByCategoryUseCase = listByCategoryUseCase;
    }

    @Override
    public ResponseEntity<CreateProductResponse> create(CreateProductRequest input) {
        var command = new CreateProductCommand(
                input.name(),
                input.description(),
                input.value(),
                input.imageUrl(),
                input.productCategoryId()
        );
        var output = createProductUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductPresenter.present(output));
    }

    @Override
    public ResponseEntity<GetProductResponse> getById(Integer id) {
        final var output = getProductUseCase.execute(id);
        return ResponseEntity.ok(ProductPresenter.present(output));
    }

    @Override
    public ResponseEntity<UpdateProductResponse> update(Integer id, UpdateProductRequest request) {
        var command = new UpdateProductCommand(
                id,
                request.name(),
                request.description(),
                request.value(),
                request.imageUrl(),
                request.productCategoryId()
        );
        var output = updateProductUseCase.execute(command);
        return ResponseEntity.ok(ProductPresenter.present(output));
    }

    @Override
    public void delete(Integer id) {
        this.deleteProductUseCase.execute(id);
    }

    @Override
    public ResponseEntity<Pagination<ListProductByCategoryResponse>> listByCategory(
            Integer categoryId,
            int page,
            int perPage,
            String sort,
            String dir,
            String search
    ) {
        var query = new SearchQuery(page, perPage, search, sort, dir);
        var params = ListByCategoryParams.with(categoryId, query);
        var result = listByCategoryUseCase.execute(params);

        final var pagination = new Pagination<>(
                result.currentPage(),
                result.perPage(),
                result.total(),
                result.items().stream()
                        .map(ProductPresenter::present)
                        .toList()
        );

        return ResponseEntity.ok(pagination);
    }
}
