package soat.project.fastfoodsoat.domain.order;

import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;

public interface OrderGateway {
    Order create(Order order);
    Order findByPublicId(OrderPublicId orderPublicId);
    Integer findLastOrderNumber();
    Pagination<Order> findAll(SearchQuery query);
}
