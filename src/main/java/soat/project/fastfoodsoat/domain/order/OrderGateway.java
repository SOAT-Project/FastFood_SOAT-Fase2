package soat.project.fastfoodsoat.domain.order;

public interface OrderGateway {
    Order create(Order order);
    Integer findLastOrderNumber();
}
