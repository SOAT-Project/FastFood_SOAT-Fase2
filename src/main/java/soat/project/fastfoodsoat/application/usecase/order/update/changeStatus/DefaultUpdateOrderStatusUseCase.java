package soat.project.fastfoodsoat.application.usecase.order.update.changeStatus;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;

import java.util.Objects;

@Transactional
@Component
public class DefaultUpdateOrderStatusUseCase extends UpdateOrderStatusUseCase {

    private final OrderGateway orderGateway;

    public DefaultUpdateOrderStatusUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public UpdateOrderStatusOutput execute(final UpdateOrderStatusCommand command) {
        final var publicId = OrderPublicId.of(command.orderPublicId());

        final var order = orderGateway.findByPublicId(publicId)
                .orElseThrow(() -> NotFoundException.with(Order.class, publicId));

        final var newStatus = OrderStatus.valueOf(command.newStatus());
        validateStatus(newStatus.name());

        final var updatedOrder = order.update(
                order.getValue(),
                order.getOrderNumber(),
                newStatus
        );

        final var savedOrder = orderGateway.update(updatedOrder);

        return UpdateOrderStatusOutput.from(savedOrder);
    }

    public void validateStatus(String status) {
        try {
            OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

}
