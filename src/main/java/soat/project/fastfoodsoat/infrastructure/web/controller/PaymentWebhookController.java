package soat.project.fastfoodsoat.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.application.command.payment.update.UpdatePaymentStatusCommand;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCase;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.PaymentWebhookAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.payment.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.payment.MercadoPagoWebhookResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.PaymentPresenter;

@RestController
public class PaymentWebhookController implements PaymentWebhookAPI {

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    public PaymentWebhookController(final UpdatePaymentStatusUseCase updatePaymentStatusUseCase) {
        this.updatePaymentStatusUseCase = updatePaymentStatusUseCase;
    }

    @Override
    public ResponseEntity<MercadoPagoWebhookResponse> handlePaymentNotification(final MercadoPagoWebhookRequest request) {
        String newStatus = mapMercadoPagoTopicToStatus(request.topic());

        final var command = new UpdatePaymentStatusCommand(
                request.id(),
                newStatus
        );

        final var output = this.updatePaymentStatusUseCase.execute(command);
        return ResponseEntity.ok(PaymentPresenter.present(output));
    }

    private String mapMercadoPagoTopicToStatus(String topic) {
        return switch (topic.toLowerCase()) {
            case "payment.created", "payment.updated" -> "APPROVED";

            default -> "REJECTED";
        };
    }
}
