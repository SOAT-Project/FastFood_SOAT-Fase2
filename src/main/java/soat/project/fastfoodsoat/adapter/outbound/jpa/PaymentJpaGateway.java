package soat.project.fastfoodsoat.adapter.outbound.jpa;

import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.PaymentRepository;
import soat.project.fastfoodsoat.domain.order.OrderId;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentGateway;

import java.util.UUID;

public class PaymentJpaGateway implements PaymentGateway {

    private final PaymentRepository paymentRepository;

    public PaymentJpaGateway(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment create(Payment payment) {
        return null;
    }

    @Override
    public Payment findByExternalReference(UUID externalReference) {
        return null;
    }

    @Override
    public Payment findById(OrderId orderId) {
        return null;
    }

    @Override
    public void update(Payment payment) {

    }
}
