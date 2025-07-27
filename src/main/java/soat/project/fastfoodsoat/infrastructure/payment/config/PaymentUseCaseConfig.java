package soat.project.fastfoodsoat.infrastructure.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import soat.project.fastfoodsoat.application.gateway.PaymentRepositoryGateway;
import soat.project.fastfoodsoat.application.gateway.QRCodeServiceGateway;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetPaymentQRCodeByExternalReferenceUseCase;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetPaymentQRCodeByExternalReferenceUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCase;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCase;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCaseImpl;

@Configuration
public class PaymentUseCaseConfig {

    private final PaymentRepositoryGateway paymentRepositoryGateway;
    private final QRCodeServiceGateway qrCodeServiceGateway;

    public PaymentUseCaseConfig(
            final PaymentRepositoryGateway paymentRepositoryGateway,
            final QRCodeServiceGateway qrCodeServiceGateway
    ) {
        this.paymentRepositoryGateway = paymentRepositoryGateway;
        this.qrCodeServiceGateway = qrCodeServiceGateway;
    }

    @Bean
    @Transactional
    public GetPaymentQRCodeByExternalReferenceUseCase getPaymentQRCodeByExternalReferenceUseCase() {
        return new GetPaymentQRCodeByExternalReferenceUseCaseImpl(paymentRepositoryGateway, qrCodeServiceGateway);
    }

    @Bean
    public GetPaymentStatusByExternalReferenceUseCase getPaymentStatusByExternalReferenceUseCase() {
        return new GetPaymentStatusByExternalReferenceUseCaseImpl(paymentRepositoryGateway);
    }

    @Bean
    @Transactional
    public UpdatePaymentStatusUseCase updatePaymentStatusUseCase() {
        return new UpdatePaymentStatusUseCaseImpl(paymentRepositoryGateway);
    }

}
