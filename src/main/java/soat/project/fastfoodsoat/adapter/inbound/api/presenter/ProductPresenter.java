package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.CreateProductResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.GetProductResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.ListProductByCategoryResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.UpdateProductResponse;
import soat.project.fastfoodsoat.application.usecase.product.create.CreateProductOutput;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.get.GetProductOutput;
import soat.project.fastfoodsoat.application.usecase.product.retrieve.list.byCategory.ListByCategoryOutput;
import soat.project.fastfoodsoat.application.usecase.product.update.UpdateProductOutput;

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
