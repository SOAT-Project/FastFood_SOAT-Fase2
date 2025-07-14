package soat.project.fastfoodsoat.application.output.payment;

public record GetPaymentStatusByExternalReferenceOutput(
        String paymentStatus
) {
    public static GetPaymentStatusByExternalReferenceOutput from(
            final String paymentStatus
    ) {
        return new GetPaymentStatusByExternalReferenceOutput(paymentStatus);
    }
}
