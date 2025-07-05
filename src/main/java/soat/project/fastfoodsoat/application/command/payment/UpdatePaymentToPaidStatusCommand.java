package soat.project.fastfoodsoat.application.command.payment;

public record UpdatePaymentToPaidStatusCommand(
        String externalReference
) {
    public static UpdatePaymentToPaidStatusCommand with(final String externalReference) {
        return new UpdatePaymentToPaidStatusCommand(externalReference);
    }
}
