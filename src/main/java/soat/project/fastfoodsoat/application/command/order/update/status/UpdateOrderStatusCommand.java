package soat.project.fastfoodsoat.application.command.order.update.status;

import java.util.UUID;

public record UpdateOrderStatusCommand(
        UUID orderPublicId,
        String newStatus
) {
    public static UpdateOrderStatusCommand with(final UUID orderPublicId,
                                                final String newStatus) {
        return new UpdateOrderStatusCommand(orderPublicId, newStatus);
    }
}