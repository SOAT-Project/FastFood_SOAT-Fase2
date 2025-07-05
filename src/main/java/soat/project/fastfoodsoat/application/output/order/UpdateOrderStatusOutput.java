package soat.project.fastfoodsoat.application.output.order;

import soat.project.fastfoodsoat.domain.order.Order;

import java.time.Instant;
import java.util.UUID;

public record UpdateOrderStatusOutput(
        UUID orderPublicId,
        String newStatus,
        Instant updatedAt
) {
    public static UpdateOrderStatusOutput from(final Order order) {
        return new UpdateOrderStatusOutput(
                order.getPublicId().getValue(),
                order.getStatus().name(),
                order.getUpdatedAt()
        );
    }
}
