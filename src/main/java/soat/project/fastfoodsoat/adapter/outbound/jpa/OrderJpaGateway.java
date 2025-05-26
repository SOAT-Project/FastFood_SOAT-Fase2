package soat.project.fastfoodsoat.adapter.outbound.jpa;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.OrderJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ClientRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ProductRepository;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderGateway;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.product.ProductId;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderJpaGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public OrderJpaGateway(OrderRepository orderRepository,
                           ProductRepository productRepository,
                           ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Order create(final Order order) {
        final Map<Integer, ProductJpaEntity> products = createMapOfProductsById(order);

        final var clientId = order.getClientId().getValue();
        final var client = clientRepository.findById(clientId)
                .orElseThrow(() -> NotFoundException.with(Client.class, order.getClientId()));

        final OrderJpaEntity orderJpa = OrderJpaMapper.toJpa(order, products, client);

        return OrderJpaMapper.fromJpa(orderRepository.save(orderJpa));
    }

    @Override
    public Order update(final Order order) {

        final var clientId = order.getClientId().getValue();
        final var clientJpa = clientRepository.findById(clientId)
                .orElseThrow(() -> NotFoundException.with(Client.class, order.getClientId()));

        final var productsMap = createMapOfProductsById(order);

        final var orderJpa = OrderJpaMapper.toJpa(order, productsMap, clientJpa);
        final var updatedOrderJpa = orderRepository.save(orderJpa);

        return OrderJpaMapper.fromJpa(updatedOrderJpa);
    }

    @Override
    public Optional<Order> findByPublicId(OrderPublicId orderPublicId) {
        return this.orderRepository.findOneByPublicId(orderPublicId.getValue())
                .map(OrderJpaMapper::fromJpa);
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
