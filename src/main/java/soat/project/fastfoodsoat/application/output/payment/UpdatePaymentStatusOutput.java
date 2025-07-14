package soat.project.fastfoodsoat.application.output.payment;

import soat.project.fastfoodsoat.domain.order.OrderPublicId;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.time.Instant;

public record UpdatePaymentStatusOutput(
        String externalReference,
        PaymentStatus newPaymentStatus,
        OrderPublicId orderPublicId,
        Instant updatedAt
) {
    public static UpdatePaymentStatusOutput from(
            String externalReference,
            PaymentStatus newPaymentStatus,
            OrderPublicId orderPublicId,
            Instant updatedAt
    ) {
        return new UpdatePaymentStatusOutput(externalReference, newPaymentStatus, orderPublicId, updatedAt);
    }
}
