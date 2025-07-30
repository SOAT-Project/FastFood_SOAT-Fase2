package soat.project.fastfoodsoat.infrastructure.order.controller;

import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.infrastructure.order.model.request.CreateOrderRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.request.UpdateOrderStatusRequest;
import soat.project.fastfoodsoat.infrastructure.order.model.response.CreateOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.ListOrderResponse;
import soat.project.fastfoodsoat.infrastructure.order.model.response.UpdateOrderStatusResponse;

public interface OrderController {

    CreateOrderResponse create(CreateOrderRequest request);

    Pagination<ListOrderResponse> list(int page, int perPage);

    Pagination<ListOrderResponse> listForStaff(final boolean onlyPaid,
                                   final String search,
                                   final int page,
                                   final int perPage,
                                   final String sort,
                                   final String direction);

    UpdateOrderStatusResponse update(String publicId, UpdateOrderStatusRequest request);



}
