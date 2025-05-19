package soat.project.fastfoodsoat.domain.payment;

import soat.project.fastfoodsoat.domain.AggregateRoot;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.validation.ValidationHandler;
import soat.project.fastfoodsoat.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment extends AggregateRoot<PaymentId>  {

    private BigDecimal value;
    private UUID externalReference;
    private String QRCode;
    private PaymentStatus status;

    protected Payment(
            PaymentId paymentId,
            BigDecimal value,
            UUID externalReference,
            String QRCode,
            PaymentStatus status,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        super(
                paymentId,
                createdAt,
                updatedAt,
                deletedAt
        );
        this.value = value;
        this.externalReference = externalReference;
        this.QRCode = QRCode;
        this.status = status;
        this.selfValidation();
    }

    public static Payment newPayment(
            final BigDecimal value,
            final UUID externalReference,
            final String QRCode,
            final PaymentStatus status
    ) {
        final PaymentId paymentId = null;
        final Instant now = Instant.now();
        return new Payment(
                paymentId,
                value,
                externalReference,
                QRCode,
                status,
                now,
                now,
                null
        );
    }

    public static Payment with(
            final PaymentId paymentId,
            final BigDecimal value,
            final UUID externalReference,
            final String QRCode,
            final PaymentStatus status,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Payment(
                paymentId,
                value,
                externalReference,
                QRCode,
                status,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Payment from(
            final Payment payment
    ) {
        return new Payment(
                payment.getId(),
                payment.value,
                payment.externalReference,
                payment.QRCode,
                payment.status,
                payment.getCreatedAt(),
                payment.getUpdatedAt(),
                payment.getDeletedAt()
        );
    }

    public Payment update(
            final BigDecimal value,
            final UUID externalReference,
            final String QRCode,
            final PaymentStatus status
    ) {
        this.value = value;
        this.externalReference = externalReference;
        this.QRCode = QRCode;
        this.status = status;
        this.selfValidation();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PaymentValidator(this, handler).validate();
    }

    private void selfValidation() {
        final Notification notification = Notification.create();

        this.validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("failed to create payment", notification);
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    public UUID getExternalReference() {
        return externalReference;
    }

    public String getQRCode() {
        return QRCode;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
