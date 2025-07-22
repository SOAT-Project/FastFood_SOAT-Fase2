package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceCommand;
import soat.project.fastfoodsoat.application.output.payment.GetPaymentStatusByExternalReferenceOutput;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCase;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.PaymentAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.GetPaymentStatusByExternalReferenceResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.PaymentPresenter;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode.GetPaymentQRCodeCommand;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetPaymentQRCodeByExternalReferenceUseCase;

import java.util.Base64;

@RestController
public class PaymentController implements PaymentAPI {

    private final GetPaymentQRCodeByExternalReferenceUseCase getPaymentQRCodeUseCase;
    private final GetPaymentStatusByExternalReferenceUseCase getPaymentStatusUseCase;

    public PaymentController(final GetPaymentQRCodeByExternalReferenceUseCase getPaymentQRCodeUseCase,
                             final GetPaymentStatusByExternalReferenceUseCase getPaymentStatusUseCase) {
        this.getPaymentQRCodeUseCase = getPaymentQRCodeUseCase;
        this.getPaymentStatusUseCase = getPaymentStatusUseCase;
    }

    @Override
    public ResponseEntity<byte[]> getQrCodeByExternalReference(String externalReference) {
        final GetPaymentQRCodeCommand command = new GetPaymentQRCodeCommand(
                externalReference
        );

        final String qrCodeBase64 = this.getPaymentQRCodeUseCase.execute(command);

        byte[] imageBytes = Base64.getDecoder().decode(qrCodeBase64);

        return ResponseEntity.ok()
                .header("Content-Type", "image/png")
                .header("Content-Length", String.valueOf(imageBytes.length))
                .body(imageBytes);
    }

    @Override
    public ResponseEntity<GetPaymentStatusByExternalReferenceResponse> getStatusByExternalReference(String id) {
        final GetPaymentStatusByExternalReferenceCommand command = new GetPaymentStatusByExternalReferenceCommand(
                id
        );

        final GetPaymentStatusByExternalReferenceOutput output = this.getPaymentStatusUseCase.execute(command);

        return ResponseEntity.ok(PaymentPresenter.present(output));
    }
}
