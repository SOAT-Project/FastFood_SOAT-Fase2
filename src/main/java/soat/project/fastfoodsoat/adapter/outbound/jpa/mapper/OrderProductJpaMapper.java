package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderProductJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ProductJpaEntity;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.order.orderproduct.OrderProductId;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderProductJpaMapper {

    private OrderProductJpaMapper() {
        // Private constructor to prevent instantiation
    }

    public static List<OrderProductJpaEntity> toJpa(final List<OrderProduct> orderProducts, final OrderJpaEntity orderJpa, final Map<Integer, ProductJpaEntity> productsJpaMap) {
        return orderProducts.stream()
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
    }

    public static OrderProduct fromJpa(final OrderProductJpaEntity orderProductJpa) {
        return OrderProduct.with(
                OrderProductId.of(orderProductJpa.getId()),
                orderProductJpa.getValue(),
                orderProductJpa.getQuantity(),
                ProductMapper.toDomain(orderProductJpa.getProduct()),
                orderProductJpa.getCreatedAt(),
                orderProductJpa.getUpdatedAt(),
                orderProductJpa.getDeletedAt()
        );
    }
}
