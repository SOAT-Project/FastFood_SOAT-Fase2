package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.application.command.order.retrieve.list.ListOrderForStaffCommand;
import soat.project.fastfoodsoat.application.usecase.order.retrieve.list.forstaff.ListOrderForStaffUseCase;
import soat.project.fastfoodsoat.infrastructure.order.controller.OrderController;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.OrderAPI;
import soat.project.fastfoodsoat.infrastructure.order.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.request.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.UpdateOrderStatusResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.presenter.OrderPresenter;
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
public class RestOrderController implements OrderAPI {

    private final OrderController orderController;

    public RestOrderController(final OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public ResponseEntity<CreateOrderResponse> create(final CreateOrderRequest createOrderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderController.create(createOrderRequest));
    }

    @Override
    public ResponseEntity<UpdateOrderStatusResponse> updateStatus(final String publicId, final UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderController.update(publicId, request));
    }

    @Override
    public ResponseEntity<Pagination<ListOrderResponse>> list(final int page, final int perPage
    ) {
        return ResponseEntity.ok(orderController.list(page, perPage));
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
       return ResponseEntity.ok(orderController.listForStaff(onlyPaid, search, page, perPage, sort, direction));
    }
}