package soat.project.fastfoodsoat.application.usecase.order.create;

import soat.project.fastfoodsoat.adapter.inbound.api.model.request.CreateOrderProductRequest;

public record CreateOrderProductCommand(
        Integer productId,
        Integer quantity
) {
    public static CreateOrderProductCommand from(final CreateOrderProductRequest request) {
        return new CreateOrderProductCommand(
                request.productId(),
                request.quantity()
        );
    }
}