package soat.project.fastfoodsoat.domain.order;


import java.util.Optional;
import soat.project.fastfoodsoat.domain.pagination.Pagination;
import soat.project.fastfoodsoat.domain.pagination.SearchQuery;


public interface OrderGateway {
    Order create(Order order);
    Order update(Order order);
    Optional<Order> findByPublicId(OrderPublicId orderPublicId);
    Integer findLastOrderNumber();
    Pagination<Order> findAll(boolean onlyPaid, SearchQuery query);
}
