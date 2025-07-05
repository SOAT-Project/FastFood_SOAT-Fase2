package soat.project.fastfoodsoat.application.gateway;

import soat.project.fastfoodsoat.domain.orderproduct.OrderProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    String createDynamicQrCode(Integer orderNumber, UUID publicId, BigDecimal value, List<OrderProduct> orderProducts);
}
