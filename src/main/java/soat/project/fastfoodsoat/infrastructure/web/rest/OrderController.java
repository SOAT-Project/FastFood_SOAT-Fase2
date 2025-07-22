package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderForStaffCommand;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff.ListOrderForStaffUseCase;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.OrderAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.order.CreateOrderRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.request.order.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.UpdateOrderStatusResponse;
import soat.project.fastfoodsoat.infrastructure.web.model.response.order.ListOrderResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.OrderPresenter;
import soat.project.fastfoodsoat.application.command.order.create.CreateOrderCommand;
import soat.project.fastfoodsoat.application.output.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.command.order.create.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderCommand;
import soat.project.fastfoodsoat.application.command.order.update.status.UpdateOrderStatusCommand;
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
    private final ListOrderForStaffUseCase listOrderForStaffUseCase;

    public OrderController(
            final CreateOrderUseCase createOrderUseCase,
            final UpdateOrderStatusUseCase updateOrderStatusUseCase,
            final ListOrderUseCase listOrderUseCase,
            final ListOrderForStaffUseCase listOrderForStaffUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.listOrderUseCase = listOrderUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
        this.listOrderForStaffUseCase = listOrderForStaffUseCase;
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

    @Override
    public ResponseEntity<Pagination<ListOrderResponse>> list(
            final int page,
            final int perPage
    ) {
        final var query = new SearchQuery(page, perPage, null, null, null);

        final var params = new ListOrderCommand(query);
        final var output = this.listOrderUseCase.execute(params);

        return ResponseEntity.ok(output.map(OrderPresenter::present));
    }

    @Override
    public ResponseEntity<Pagination<ListOrderResponse>> listForStaff(
            final boolean onlyPaid,
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var params = new ListOrderForStaffCommand(onlyPaid, query);
        final var output = this.listOrderForStaffUseCase.execute(params);

        return ResponseEntity.ok(output.map(OrderPresenter::present));
    }
}