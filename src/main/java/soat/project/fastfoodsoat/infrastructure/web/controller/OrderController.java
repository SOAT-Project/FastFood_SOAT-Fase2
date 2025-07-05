package soat.project.fastfoodsoat.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.OrderAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.order.CreateOrderRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.request.order.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.UpdateOrderStatusResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.ListOrderResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.OrderPresenter;
import soat.project.fastfoodsoat.application.command.order.CreateOrderCommand;
import soat.project.fastfoodsoat.application.output.order.CreateOrderOutput;
import soat.project.fastfoodsoat.application.command.order.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;
import soat.project.fastfoodsoat.application.command.order.ListOrderParams;
import soat.project.fastfoodsoat.application.command.order.UpdateOrderStatusCommand;
import soat.project.fastfoodsoat.application.usecase.order.update.status.UpdateOrderStatusUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderUseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import java.util.UUID;


@RestController
public class OrderController implements OrderAPI {

    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final ListOrderUseCase listOrderUseCase;

    public OrderController(
            final CreateOrderUseCase createOrderUseCase,
            final UpdateOrderStatusUseCase updateOrderStatusUseCase,
            final ListOrderUseCase listOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.listOrderUseCase = listOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @Override
    public ResponseEntity<CreateOrderResponse> create(final CreateOrderRequest createOrderRequest) {
        final CreateOrderCommand command = new CreateOrderCommand(
                createOrderRequest.clientPublicId(),
                createOrderRequest.orderProducts()
                        .stream()
                        .map(CreateOrderProductCommand::from)
                        .toList()
        );

        final CreateOrderOutput output = this.createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderPresenter.present(output));
    }

    @Override
    public ResponseEntity<UpdateOrderStatusResponse> updateStatus(final String publicId, final UpdateOrderStatusRequest request) {
        var command = new UpdateOrderStatusCommand(UUID.fromString(publicId), request.status());
        var output = updateOrderStatusUseCase.execute(command);
        return ResponseEntity.ok(new UpdateOrderStatusResponse(output.orderPublicId().toString(), output.newStatus()));
    }


    public ResponseEntity<Pagination<ListOrderResponse>> list(
            final boolean onlyPaid,
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var params = new ListOrderParams(onlyPaid, query);
        final var output = this.listOrderUseCase.execute(params);

        return ResponseEntity.ok(output.map(OrderPresenter::present));
    }
}