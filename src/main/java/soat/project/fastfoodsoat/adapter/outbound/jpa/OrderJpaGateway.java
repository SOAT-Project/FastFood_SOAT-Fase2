package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.OrderJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.OrderProductRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderJpaGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    public OrderJpaGateway(OrderRepository orderRepository,
                           OrderProductRepository orderProductRepository,
                           ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create(final Order order) {
        final var orderJpa = OrderJpaMapper.toJpa(order);
        final var products = createMapOfProductsById(order);
        final var orderProducts = OrderJpaMapper.toJpa(order.getOrderProducts(), orderJpa, products);
        orderJpa.setOrderProducts(orderProducts);

        return OrderJpaMapper.fromJpa(orderRepository.save(orderJpa));
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
