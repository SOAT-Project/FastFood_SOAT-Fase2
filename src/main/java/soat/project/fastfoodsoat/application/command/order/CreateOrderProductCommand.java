package soat.project.fastfoodsoat.application.command.order;

import soat.project.fastfoodsoat.infrastructure.web.model.request.order.CreateOrderProductRequest;

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