package soat.project.fastfoodsoat.infrastructure.payment.controller;

import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.application.command.payment.update.UpdatePaymentStatusCommand;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCase;
import soat.project.fastfoodsoat.infrastructure.payment.model.request.MercadoPagoWebhookRequest;
import soat.project.fastfoodsoat.infrastructure.payment.model.response.MercadoPagoWebhookResponse;
import soat.project.fastfoodsoat.infrastructure.payment.presenter.PaymentPresenter;

@Component
public class PaymentWebhookControllerImpl implements PaymentWebhookController {

    private final UpdatePaymentStatusUseCase updatePaymentStatusUseCase;

    public PaymentWebhookControllerImpl(final UpdatePaymentStatusUseCase updatePaymentStatusUseCase) {
        this.updatePaymentStatusUseCase = updatePaymentStatusUseCase;
    }

    @Override
    public MercadoPagoWebhookResponse handlePaymentNotification(final MercadoPagoWebhookRequest request) {
        String newStatus = mapMercadoPagoTopicToStatus(request.topic());

        final var command = new UpdatePaymentStatusCommand(
                request.id(),
                newStatus
        );

        final var output = this.updatePaymentStatusUseCase.execute(command);
        return PaymentPresenter.present(output);
    }

    private String mapMercadoPagoTopicToStatus(String topic) {
        return switch (topic.toLowerCase()) {
            case "payment.created", "payment.updated" -> "APPROVED";

            default -> "REJECTED";
        };
    }

}
