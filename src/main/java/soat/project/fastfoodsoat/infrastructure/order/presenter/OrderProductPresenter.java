package soat.project.fastfoodsoat.infrastructure.order.presenter;

import soat.project.fastfoodsoat.infrastructure.order.model.response.CreateOrderProductResponse;
import soat.project.fastfoodsoat.application.output.order.create.CreateOrderProductOutput;

public interface OrderProductPresenter {

    static CreateOrderProductResponse present(final CreateOrderProductOutput command) {
        return new CreateOrderProductResponse(
                command.productId(),
                command.productName(),
                command.quantity()
        );
    }
}
