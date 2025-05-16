package soat.project.fastfoodsoat.application.usecase.order.create;

import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProduct;

public record CreateOrderProductOutput(
        Integer productId,
        String productName,
        Integer quantity
) {
    public static CreateOrderProductOutput from(
            final Integer productId,
            final String productName,
            final Integer quantity
    ) {
        return new CreateOrderProductOutput(productId, productName, quantity);
    }

    public static CreateOrderProductOutput from(final OrderProduct orderProduct) {
        return new CreateOrderProductOutput(
                orderProduct.getProduct().getId().getValue(),
                orderProduct.getProduct().getName(),
                orderProduct.getQuantity()
        );
    }
}
