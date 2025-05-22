package soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode;

import java.util.UUID;

public record GetQRCodeCommand(UUID orderPublicId) {
    public static GetQRCodeCommand with(final UUID orderPublicId)
    {
        return new GetQRCodeCommand(orderPublicId);
    }
}

