package soat.project.fastfoodsoat.infrastructure.product.presenter;

import soat.project.fastfoodsoat.infrastructure.product.model.response.CreateProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.GetProductResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.infrastructure.product.model.response.UpdateProductResponse;
import soat.project.fastfoodsoat.application.output.product.create.CreateProductOutput;
import soat.project.fastfoodsoat.application.output.product.GetProductOutput;
import soat.project.fastfoodsoat.application.output.product.ListByCategoryOutput;
import soat.project.fastfoodsoat.application.output.product.update.UpdateProductOutput;

public interface ProductPresenter {

    static ListProductByCategoryResponse present(final ListByCategoryOutput output) {
        return new ListProductByCategoryResponse(
                output.id(),
                output.name(),
                output.description(),
                output.value(),
                output.imageURL(),
                output.productCategoryId(),
                output.createdAt(),
                output.updatedAt()

        );
    }

    static GetProductResponse present(final GetProductOutput output) {
        return new GetProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.value(),
                output.imageURL(),
                output.productCategoryId(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static CreateProductResponse present(final CreateProductOutput output) {
        return new CreateProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.value(),
                output.imageURL(),
                output.productCategoryId(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static UpdateProductResponse present(final UpdateProductOutput output) {
        return new UpdateProductResponse(
                output.id(),
                output.name(),
                output.description(),
                output.value(),
                output.imageURL(),
                output.productCategoryId(),
                output.updatedAt()
        );
    }
}
