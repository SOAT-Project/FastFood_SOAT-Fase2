package soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode;

public record GetPaymentQRCodeCommand(String externalReference) {
    public static GetPaymentQRCodeCommand with(final String externalReference)
    {
        return new GetPaymentQRCodeCommand(externalReference);
    }
}

