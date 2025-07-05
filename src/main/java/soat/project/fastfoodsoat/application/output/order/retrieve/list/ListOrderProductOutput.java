package soat.project.fastfoodsoat.application.output.order.retrieve.list;

import soat.project.fastfoodsoat.domain.orderproduct.OrderProduct;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public record ListOrderProductOutput(
        BigDecimal value,
        Integer quantity,
        Integer productId,
        String productName
) {
    public static ListOrderProductOutput from(final OrderProduct orderProduct) {
        return new ListOrderProductOutput(
                orderProduct.getValue(),
                orderProduct.getQuantity(),
                isNull(orderProduct.getProduct()) ? null : orderProduct.getProduct().getId().getValue(),
                isNull(orderProduct.getProduct()) ? null : orderProduct.getProduct().getName()
        );
    }

}
