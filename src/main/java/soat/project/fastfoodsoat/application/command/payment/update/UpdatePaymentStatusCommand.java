package soat.project.fastfoodsoat.application.command.payment.update;

public record UpdatePaymentStatusCommand(
        String externalReference,
        String newStatus
) {}
