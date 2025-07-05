package soat.project.fastfoodsoat.application.usecase.order.update.status;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.order.update.status.UpdateOrderStatusCommand;
import soat.project.fastfoodsoat.application.output.order.update.status.UpdateOrderStatusOutput;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.application.gateway.OrderRepositoryGateway;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;

import java.util.Objects;

@Transactional
@Component
public class UpdateOrderStatusUseCaseImpl extends UpdateOrderStatusUseCase {

    private final OrderRepositoryGateway orderRepositoryGateway;

    public UpdateOrderStatusUseCaseImpl(final OrderRepositoryGateway orderRepositoryGateway) {
        this.orderRepositoryGateway = Objects.requireNonNull(orderRepositoryGateway);
    }

    @Override
    public UpdateOrderStatusOutput execute(final UpdateOrderStatusCommand command) {
        final var publicId = OrderPublicId.of(command.orderPublicId());

        final var order = orderRepositoryGateway.findByPublicId(publicId)
                .orElseThrow(() -> NotFoundException.with(Order.class, publicId));

        final var newStatus = OrderStatus.valueOf(command.newStatus());
        validateStatus(newStatus.name());

        final var updatedOrder = order.update(
                order.getValue(),
                order.getOrderNumber(),
                newStatus
        );

        final var savedOrder = orderRepositoryGateway.update(updatedOrder);

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
