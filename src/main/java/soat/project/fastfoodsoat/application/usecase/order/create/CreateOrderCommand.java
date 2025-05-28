package soat.project.fastfoodsoat.application.usecase.order.create;

import java.util.List;
import java.util.Optional;

public record CreateOrderCommand(
        Integer clientId,
        List<CreateOrderProductCommand> orderProducts
) {
    public static CreateOrderCommand with(
            final Integer clientId,
            final List<CreateOrderProductCommand> orderProducts
    ) {
        return new CreateOrderCommand(
                clientId,
                orderProducts
        );
    }
}
