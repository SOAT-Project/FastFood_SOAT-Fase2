package soat.project.fastfoodsoat.application.output.order;

import soat.project.fastfoodsoat.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

public record ListOrderOutput(
        UUID publicId,
        BigDecimal value,
        Integer orderNumber,
        String status,
        List<ListOrderProductOutput> products
) {
    public static ListOrderOutput from(final Order order) {
        return new ListOrderOutput(
                isNull(order.getPublicId()) ? null : order.getPublicId().getValue(),
                order.getValue(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getOrderProducts().stream().map(ListOrderProductOutput::from).toList()
        );
    }
}
