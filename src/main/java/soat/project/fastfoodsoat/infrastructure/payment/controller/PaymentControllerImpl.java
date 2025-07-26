package soat.project.fastfoodsoat.infrastructure.payment.controller;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode.GetPaymentQRCodeCommand;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceCommand;
import soat.project.fastfoodsoat.application.output.payment.GetPaymentStatusByExternalReferenceOutput;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetPaymentQRCodeByExternalReferenceUseCase;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCase;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.GetPaymentStatusByExternalReferenceResponse;
import soat.project.fastfoodsoat.infrastructure.payment.presenter.PaymentPresenter;

import java.util.Base64;

@Component
public class PaymentControllerImpl implements PaymentController {

    private final GetPaymentQRCodeByExternalReferenceUseCase getPaymentQRCodeUseCase;
    private final GetPaymentStatusByExternalReferenceUseCase getPaymentStatusUseCase;

    public PaymentControllerImpl(
            final GetPaymentQRCodeByExternalReferenceUseCase getPaymentQRCodeUseCase,
            final GetPaymentStatusByExternalReferenceUseCase getPaymentStatusUseCase
    ) {
        this.getPaymentQRCodeUseCase = getPaymentQRCodeUseCase;
        this.getPaymentStatusUseCase = getPaymentStatusUseCase;
    }

    @Override
    public byte[] getQrCodeByExternalReference(final String externalReference) {
        final GetPaymentQRCodeCommand command = new GetPaymentQRCodeCommand(
                externalReference
        );

        final String qrCodeBase64 = this.getPaymentQRCodeUseCase.execute(command);

        return Base64.getDecoder().decode(qrCodeBase64);
    }

    @Override
    public GetPaymentStatusByExternalReferenceResponse getStatusByExternalReference(final String externalReference) {
        final GetPaymentStatusByExternalReferenceCommand command = new GetPaymentStatusByExternalReferenceCommand(
                externalReference
        );

        final GetPaymentStatusByExternalReferenceOutput output = this.getPaymentStatusUseCase.execute(command);

        return PaymentPresenter.present(output);
    }

}
