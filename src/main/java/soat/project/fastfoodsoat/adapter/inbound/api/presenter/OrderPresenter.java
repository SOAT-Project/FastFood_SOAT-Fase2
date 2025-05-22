package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderProductResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderOutput;

public interface OrderPresenter {

    static CreateOrderResponse present(final CreateOrderOutput output) {
        return new CreateOrderResponse(
                output.publicId().toString(),
                output.orderNumber(),
                output.status(),
                output.value().toString(),
                output.paymentStatus(),
                output.paymentExternalReference(),
                output.qrCode(),
                output.orderProducts()
                        .stream()
                        .map(OrderProductPresenter::present)
                        .toArray(CreateOrderProductResponse[]::new)
        );
    }
}
