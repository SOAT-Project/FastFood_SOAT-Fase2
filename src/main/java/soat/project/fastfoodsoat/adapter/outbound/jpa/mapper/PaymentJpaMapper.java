package soat.project.fastfoodsoat.adapter.outbound.jpa.mapper;

import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.PaymentJpaEntity;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentId;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

public class PaymentJpaMapper {

    private PaymentJpaMapper() {
        // Private constructor to prevent instantiation
    }

    public static PaymentJpaEntity toJpa(final Payment payment) {
        if (payment == null) return new PaymentJpaEntity();

        return new PaymentJpaEntity(
                payment.getValue(),
                payment.getExternalReference(),
                payment.getQRCode(),
                payment.getStatus().toString(),
                payment.getCreatedAt(),
                payment.getUpdatedAt(),
                payment.getDeletedAt()
        );
    }

    public static Payment fromJpa(final PaymentJpaEntity paymentJpa) {
        return Payment.with(
                PaymentId.of(paymentJpa.getId()),
                paymentJpa.getValue(),
                paymentJpa.getExternalReference(),
                paymentJpa.getQrCode(),
                PaymentStatus.valueOf(paymentJpa.getStatus()),
                paymentJpa.getCreatedAt(),
                paymentJpa.getUpdatedAt(),
                paymentJpa.getDeletedAt()
        );
    }
}
