package soat.project.fastfoodsoat.application.gateway;

import soat.project.fastfoodsoat.domain.payment.Payment;

import java.util.Optional;

public interface PaymentRepositoryGateway {
    Payment create(Payment payment);
    Optional<Payment> findByExternalReference(String externalReference);
    Payment update(Payment payment);
}
