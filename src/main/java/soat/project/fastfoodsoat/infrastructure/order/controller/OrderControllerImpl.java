package soat.project.fastfoodsoat.infrastructure.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.order.create.CreateOrderCommand;
import soat.project.fastfoodsoat.application.command.order.create.CreateOrderProductCommand;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderCommand;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderForStaffCommand;
import soat.project.fastfoodsoat.application.command.order.update.status.UpdateOrderStatusCommand;
import soat.project.fastfoodsoat.application.output.order.create.CreateOrderOutput;
import soat.project.fastfoodsoat.application.usecase.order.create.CreateOrderUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.ListOrderUseCase;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff.ListOrderForStaffUseCase;
import soat.project.fastfoodsoat.application.usecase.order.update.status.UpdateOrderStatusUseCase;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;
import soat.project.fastfoodsoat.infrastructure.order.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.request.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.UpdateOrderStatusResponse;
import soat.project.fastfoodsoat.infrastructure.order.presenter.OrderPresenter;

import java.util.UUID;

@Component
public class OrderControllerImpl implements OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final ListOrderUseCase listOrderUseCase;
    private final ListOrderForStaffUseCase listOrderForStaffUseCase;
    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public OrderControllerImpl(final CreateOrderUseCase createOrderUseCase,
                               final ListOrderUseCase listOrderUseCase,
                               final ListOrderForStaffUseCase listOrderForStaffUseCase,
                               final UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.listOrderUseCase = listOrderUseCase;
        this.listOrderForStaffUseCase = listOrderForStaffUseCase;
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @Override
    public CreateOrderResponse create(CreateOrderRequest request) {
        final CreateOrderCommand command = new CreateOrderCommand(
                request.clientPublicId(),
                request.orderProducts()
                        .stream()
                        .map(CreateOrderProductCommand::from)
                        .toList()
        );

        final CreateOrderOutput output = this.createOrderUseCase.execute(command);
        return OrderPresenter.present(output);
    }

    @Override
    public Pagination<ListOrderResponse> list(int page, int perPage) {
        final var query = new SearchQuery(page, perPage, null, null, null);

        final var params = new ListOrderCommand(query);
        final var output = this.listOrderUseCase.execute(params);

        return output.map(OrderPresenter::present);
    }

    @Override
    public Pagination<ListOrderResponse> listForStaff(boolean onlyPaid, String search, int page, int perPage, String sort, String direction) {
        final var query = new SearchQuery(page, perPage, search, sort, direction);

        final var params = new ListOrderForStaffCommand(onlyPaid, query);
        final var output = this.listOrderForStaffUseCase.execute(params);

        return output.map(OrderPresenter::present);
    }

    @Override
    public UpdateOrderStatusResponse update(String publicId, UpdateOrderStatusRequest request) {
        var command = new UpdateOrderStatusCommand(UUID.fromString(publicId), request.status());
        var output = updateOrderStatusUseCase.execute(command);
        return new UpdateOrderStatusResponse(output.orderPublicId().toString(), output.newStatus());
    }
}
