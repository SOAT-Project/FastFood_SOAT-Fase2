package soat.project.fastfoodsoat.application.usecase.order.create;

import soat.project.fastfoodsoat.domain.order.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderOutput(
        UUID publicId,
        BigDecimal value,
        Integer orderNumber,
        String status,
        List<CreateOrderProductOutput> orderProducts,
        String createdAt,
        String updatedAt,
        String deletedAt
) {
    public static CreateOrderOutput from(
            final UUID publicId,
            final BigDecimal value,
            final Integer orderNumber,
            final String status,
            final List<CreateOrderProductOutput> orderProducts,
            final String createdAt,
            final String updatedAt,
            final String deletedAt
    ) {
        return new CreateOrderOutput(publicId, value, orderNumber, status, orderProducts, createdAt, updatedAt, deletedAt);
    }

    public static CreateOrderOutput from(final Order order) {
        return new CreateOrderOutput(
                order.getPublicId().getValue(),
                order.getValue(),
                order.getOrderNumber(),
                order.getStatus().toString(),
                order.getOrderProducts().stream()
                        .map(CreateOrderProductOutput::from)
                        .toList(),
                order.getCreatedAt().toString(),
                order.getUpdatedAt().toString(),
                order.getDeletedAt() != null ? order.getDeletedAt().toString() : null
        );
    }
}
