package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderPaymentResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderProductResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.ListOrderProductResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderProductOutput;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

public interface OrderPresenter {

    static CreateOrderResponse present(final CreateOrderOutput output) {
        return new CreateOrderResponse(
                output.publicId().toString(),
                output.orderNumber(),
                output.status(),
                output.value().toString(),
                new CreateOrderPaymentResponse(
                        output.payment().status(),
                        output.payment().externalReference(),
                        output.payment().qrCode()
                ),
                output.orderProducts()
                        .stream()
                        .map(OrderProductPresenter::present)
                        .toArray(CreateOrderProductResponse[]::new)
        );
    }

    static ListOrderResponse present(final ListOrderOutput output) {
        return new ListOrderResponse(
                output.publicId(),
                output.value(),
                output.orderNumber(),
                output.status(),
                isNull(output.products()) ? List.of() : output.products().stream()
                        .map(OrderPresenter::present)
                        .toList()
        );
    }

    static ListOrderProductResponse present(final ListOrderProductOutput output) {
        return new ListOrderProductResponse(
                output.value(),
                output.quantity(),
                output.productId(),
                output.productName()
        );
    }
}
