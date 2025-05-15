package soat.project.fastfoodsoat.domain.order;

import java.util.Optional;

public interface OrderGateway {
    Order create(Order order);
}
