package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderProductResponse;
import soat.project.fastfoodsoat.application.output.order.CreateOrderProductOutput;

public interface OrderProductPresenter {

    static CreateOrderProductResponse present(final CreateOrderProductOutput command) {
        return new CreateOrderProductResponse(
                command.productId(),
                command.productName(),
                command.quantity()
        );
    }
}
