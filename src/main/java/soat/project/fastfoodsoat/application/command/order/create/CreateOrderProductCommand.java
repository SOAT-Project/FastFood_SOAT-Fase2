package soat.project.fastfoodsoat.application.command.order.create;

import soat.project.fastfoodsoat.infrastructure.order.model.request.CreateOrderProductRequest;

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