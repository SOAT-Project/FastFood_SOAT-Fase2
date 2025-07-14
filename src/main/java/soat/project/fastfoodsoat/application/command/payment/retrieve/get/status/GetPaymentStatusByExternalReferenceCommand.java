package soat.project.fastfoodsoat.application.command.payment.retrieve.get.status;

public record GetPaymentStatusByExternalReferenceCommand(String externalReference) {
    public static GetPaymentStatusByExternalReferenceCommand with(final String externalReference)
    {
        return new GetPaymentStatusByExternalReferenceCommand(externalReference);
    }
}
