package soat.project.fastfoodsoat.application.output.order.retrieve.list;

import soat.project.fastfoodsoat.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

public record ListOrderForStaffOutput(
        UUID publicId,
        BigDecimal value,
        Integer orderNumber,
        String status,
        List<ListOrderProductOutput> products
) {
    public static ListOrderForStaffOutput from(final Order order) {
        return new ListOrderForStaffOutput(
                isNull(order.getPublicId()) ? null : order.getPublicId().getValue(),
                order.getValue(),
                order.getOrderNumber(),
                order.getStatus().name(),
                order.getOrderProducts().stream().map(ListOrderProductOutput::from).toList()
        );
    }
}
