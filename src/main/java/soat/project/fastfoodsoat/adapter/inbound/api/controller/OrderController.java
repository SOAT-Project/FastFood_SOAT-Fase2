package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.adapter.inbound.api.OrderAPI;
import soat.project.fastfoodsoat.adapter.inbound.api.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.adapter.inbound.api.presenter.OrderPresenter;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderUseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

@RestController
public class OrderController implements OrderAPI {

    private final CreateOrderUseCase createOrderUseCase;
    private final ListOrderUseCase listOrderUseCase;

    public OrderController(
            final CreateOrderUseCase createOrderUseCase,
            final ListOrderUseCase listOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.listOrderUseCase = listOrderUseCase;
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

    @Override
    public ResponseEntity<Pagination<ListOrderResponse>> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var output = this.listOrderUseCase.execute(query);

        return ResponseEntity.ok(output.map(OrderPresenter::present));
    }
}