package soat.project.fastfoodsoat.application.output.payment;

import java.math.BigDecimal;

public record UpdatePaymentToPaidStatusOutput(
        BigDecimal value,
        String paymentStatus,
        String externalReference
) {
    public static UpdatePaymentToPaidStatusOutput from(
            final BigDecimal value,
            final String paymentStatus,
            final String externalReference
    ) {
        return new UpdatePaymentToPaidStatusOutput(value, paymentStatus, externalReference);
    }
}
