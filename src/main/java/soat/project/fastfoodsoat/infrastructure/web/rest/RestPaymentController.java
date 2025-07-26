package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.payment.controller.PaymentController;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.GetPaymentStatusByExternalReferenceResponse;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.PaymentAPI;

@RestController
public class RestPaymentController implements PaymentAPI {

    private final PaymentController paymentController;

    public RestPaymentController(final PaymentController paymentController) {
        this.paymentController = paymentController;
    }

    @Override
    public ResponseEntity<byte[]> getQrCodeByExternalReference(final String externalReference) {
        final byte[] imageBytes = paymentController.getQrCodeByExternalReference(externalReference);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .header("Content-Length", String.valueOf(imageBytes.length))
                .body(imageBytes);
    }

    @Override
    public ResponseEntity<GetPaymentStatusByExternalReferenceResponse> getStatusByExternalReference(
            final String externalReference
    ) {
        return ResponseEntity.ok(this.paymentController.getStatusByExternalReference(externalReference));
    }
}
