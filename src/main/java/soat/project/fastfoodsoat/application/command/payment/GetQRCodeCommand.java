package soat.project.fastfoodsoat.application.command.payment;

public record GetQRCodeCommand(String externalReference) {
    public static GetQRCodeCommand with(final String externalReference)
    {
        return new GetQRCodeCommand(externalReference);
    }
}

