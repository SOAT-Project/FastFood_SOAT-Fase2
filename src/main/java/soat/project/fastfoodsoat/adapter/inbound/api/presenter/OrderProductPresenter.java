package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderProductResponse;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderProductOutput;

public interface OrderProductPresenter {

    static CreateOrderProductResponse present(final CreateOrderProductOutput command) {
        return new CreateOrderProductResponse(
                command.productId(),
                command.productName(),
                command.quantity()
        );
    }
}
