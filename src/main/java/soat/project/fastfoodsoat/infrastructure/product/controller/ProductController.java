package soat.project.fastfoodsoat.infrastructure.product.controller;

import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.infrastructure.product.model.request.CreateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.request.UpdateProductRequest;
import soat.project.fastfoodsoat.infrastructure.product.model.response.CreateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.GetProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.UpdateProductResponse;

public interface ProductController {

    CreateProductResponse create(CreateProductRequest request);

    GetProductResponse getById(Integer id);

    UpdateProductResponse update(Integer id,UpdateProductRequest request);

    void delete(Integer id);

    Pagination<ListProductByCategoryResponse> listByCategory(
            Integer categoryId,
            int page,
            int perPage,
            String sort,
            String dir,
            String search
    );

}
