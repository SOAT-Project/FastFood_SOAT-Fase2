package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.OrderJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderJpaGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderJpaGateway(OrderRepository orderRepository,
                           ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create(final Order order) {
        final Map<Integer, ProductJpaEntity> products = createMapOfProductsById(order);
        final OrderJpaEntity orderJpa = OrderJpaMapper.toJpa(order, products);

        return OrderJpaMapper.fromJpa(orderRepository.save(orderJpa));
    }

    @Override
    public Order findByPublicId(OrderPublicId orderPublicId) {
        return this.orderRepository.findOneByPublicId(orderPublicId.getValue())
                .map(OrderJpaMapper::fromJpa)
                .orElseThrow(() -> NotFoundException.with(Order.class, null));
    }

    @Override
    public Integer findLastOrderNumber() {
        return this.orderRepository.findMaxOrderNumber()
                .orElse(0);
    }

    private Map<Integer, ProductJpaEntity> createMapOfProductsById(final Order order) {
        return  order.getOrderProducts().stream()
                .map(p -> p.getProduct().getId().getValue())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        id -> productRepository.findById(id)
                                .orElseThrow(() -> NotFoundException.with(Product.class, new ProductId(id)))
                ));
    }
}
