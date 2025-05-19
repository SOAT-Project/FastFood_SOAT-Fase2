package soat.project.fastfoodsoat.domain.payment;

import soat.project.fastfoodsoat.domain.order.OrderId;

import java.util.UUID;

public interface PaymentGateway {
    Payment create(Payment payment);
    Payment findByExternalReference(UUID externalReference);
    Payment findById(OrderId orderId);
    void update(Payment payment);
}
