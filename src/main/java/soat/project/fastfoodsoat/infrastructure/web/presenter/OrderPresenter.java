package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderPaymentResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderProductResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.ListOrderProductResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.ListOrderResponse;
import soat.project.fastfoodsoat.application.output.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.application.output.order.retrieve.list.ListOrderProductOutput;

import java.util.List;

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
