package soat.project.fastfoodsoat.adapter.inbound.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.adapter.inbound.api.PaymentAPI;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetQRCodeCommand;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetQRCodeUseCase;

import java.util.Base64;
import java.util.UUID;

@RestController
public class PaymentController implements PaymentAPI {

    private final GetQRCodeUseCase getQRCodeUseCase;

    public PaymentController(final GetQRCodeUseCase getQRCodeUseCase) {
        this.getQRCodeUseCase = getQRCodeUseCase;
    }

    @Override
    public ResponseEntity<byte[]> getByOrderPublicId(String orderPublicId) {
        final GetQRCodeCommand command = new GetQRCodeCommand(
                UUID.fromString(orderPublicId)
        );

        final String qrCodeBase64 = this.getQRCodeUseCase.execute(command);

        byte[] imageBytes = Base64.getDecoder().decode(qrCodeBase64);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .header("Content-Length", String.valueOf(imageBytes.length))
                .body(imageBytes);
    }
}
