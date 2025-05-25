package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.adapter.inbound.api.OrderAPI;
import soat.project.fastfoodsoat.adapter.inbound.api.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.presenter.OrderPresenter;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;

@RestController
public class OrderController implements OrderAPI {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(final CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public ResponseEntity<CreateOrderResponse> create(final CreateOrderRequest createOrderRequest) {
        final CreateOrderCommand command = new CreateOrderCommand(
                createOrderRequest.clientId(),
                createOrderRequest.orderProducts()
                        .stream()
                        .map(CreateOrderProductCommand::from)
                        .toList()
        );

        final CreateOrderOutput output = this.createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderPresenter.present(output));
    }
}