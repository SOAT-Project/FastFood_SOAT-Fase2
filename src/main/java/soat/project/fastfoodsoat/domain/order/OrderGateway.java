package soat.project.fastfoodsoat.domain.order;

public interface OrderGateway {
    Order create(Order order);
    Order findByPublicId(OrderPublicId orderPublicId);
    Integer findLastOrderNumber();
}
