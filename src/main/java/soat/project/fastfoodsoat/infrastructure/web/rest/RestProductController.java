package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.infrastructure.product.controller.ProductController;
import soat.project.fastfoodsoat.infrastructure.product.model.request.CreateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.request.UpdateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.response.CreateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.GetProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.UpdateProductResponse;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.ProductAPI;

@RestController
public class RestProductController implements ProductAPI {

    private final ProductController productController;

    public RestProductController(final ProductController productController) {
        this.productController = productController;
    }

    @Override
    public ResponseEntity<CreateProductResponse> create(CreateProductRequest input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productController.create(input));
    }

    @Override
    public ResponseEntity<GetProductResponse> getById(Integer id) {
        return ResponseEntity.ok(productController.getById(id));
    }

    @Override
    public ResponseEntity<UpdateProductResponse> update(Integer id, UpdateProductRequest request) {
        return ResponseEntity.ok(productController.update(id,request));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        this.productController.delete(id);
        return ResponseEntity.noContent().build();
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
        return ResponseEntity.ok(this.productController.listByCategory(categoryId, page, perPage, sort, dir, search));
    }
}
