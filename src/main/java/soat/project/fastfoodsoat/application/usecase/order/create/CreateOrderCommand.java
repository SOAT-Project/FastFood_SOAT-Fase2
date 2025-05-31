package soat.project.fastfoodsoat.application.usecase.order.create;

import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID clientPublicId,
        List<CreateOrderProductCommand> orderProducts
) {
    public static CreateOrderCommand with(
            final UUID clientPublicId,
            final List<CreateOrderProductCommand> orderProducts
    ) {
        return new CreateOrderCommand(
                clientPublicId,
                orderProducts
        );
    }
}
