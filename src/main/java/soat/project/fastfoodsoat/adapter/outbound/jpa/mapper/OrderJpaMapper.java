package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.domain.order.Order;
import soat.project.fastfoodsoat.domain.order.OrderId;
import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProductId;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OrderJpaMapper {

    private OrderJpaMapper() {
        // Private constructor to prevent instantiation
    }

    public static Order fromJpa(final OrderJpaEntity orderJpa) {
        return Order.with(
                OrderId.of(orderJpa.getId()),
                OrderPublicId.of(orderJpa.getPublicId()),
                orderJpa.getValue(),
                orderJpa.getOrderNumber(),
                OrderStatus.valueOf(orderJpa.getStatus()),
                orderJpa.getOrderProducts().stream()
                        .filter(Objects::nonNull)
                        .map(orderProduct -> OrderProduct.with(
                                OrderProductId.of(orderProduct.getId()),
                                orderProduct.getValue(),
                                orderProduct.getQuantity(),
                                orderProduct.getProduct(),
                                orderProduct.getCreatedAt(),
                                orderProduct.getUpdatedAt(),
                                orderProduct.getDeletedAt()
                        ))
                        .toList(),
                orderJpa.getCreatedAt(),
                orderJpa.getUpdatedAt(),
                orderJpa.getDeletedAt()
        );
    }

    public static OrderJpaEntity toJpa(final Order order) {
        if (Objects.isNull(order)) return new OrderJpaEntity();

        return new OrderJpaEntity(
                Objects.isNull(order.getId()) ? null : order.getId().getValue(),
                Objects.isNull(order.getPublicId()) ? null : order.getPublicId().getValue(),
                order.getValue(),
                order.getOrderNumber(),
                order.getStatus().toString(),
                null,
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getDeletedAt()
        );
    }

    public static List<OrderProductJpaEntity> toJpa(final List<OrderProduct> orderProducts, final OrderJpaEntity orderJpa, final Map<Integer, ProductJpaEntity> productsJpaMap) {
        final var test = orderProducts.stream()
                .filter(Objects::nonNull)
                .map(orderProduct ->
                    {
                        ProductJpaEntity productJpaEntity = productsJpaMap.get(orderProduct.getProduct().getId().getValue());

                        return new OrderProductJpaEntity(
                                Objects.isNull(orderProduct.getId()) ? null : orderProduct.getId().getValue(),
                                orderProduct.getValue(),
                                orderProduct.getQuantity(),
                                orderJpa,
                                productJpaEntity,
                                orderProduct.getCreatedAt(),
                                orderProduct.getUpdatedAt(),
                                orderProduct.getDeletedAt()
                        );
                    }
                )
                .toList();

        return test;
    }
}
