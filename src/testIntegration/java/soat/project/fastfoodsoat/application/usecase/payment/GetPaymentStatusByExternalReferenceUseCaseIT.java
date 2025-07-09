package soat.project.fastfoodsoat.application.usecase.payment;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.qrcode.GetPaymentQRCodeCommand;
import soat.project.fastfoodsoat.application.command.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceCommand;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.status.GetPaymentStatusByExternalReferenceUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.payment.Payment;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.PaymentJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@IntegrationTest
public class GetPaymentStatusByExternalReferenceUseCaseIT {

    @Autowired
    private GetPaymentStatusByExternalReferenceUseCaseImpl useCase;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    @Transactional
    @Rollback
    void givenValidCommand_whenGetPaymentStatusByExternalReferenceUseCase_thenShouldReturnStatus() {
        OrderJpaEntity order = new OrderJpaEntity();
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.RECEIVED);
        order.setValue(BigDecimal.valueOf(100.00));
        order.setOrderNumber(1);
        OrderJpaEntity savedOrder = orderRepository.save(order);

        final String externalReference = "ext-ref-" + UUID.randomUUID();

        PaymentJpaEntity payment = new PaymentJpaEntity();
        payment.setOrder(savedOrder);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setValue(BigDecimal.valueOf(100.00));
        payment.setQrCode("QRCodeData");
        payment.setExternalReference(externalReference);
        final var savedPayment = paymentRepository.save(payment);

        final var command = new GetPaymentStatusByExternalReferenceCommand(externalReference);

        final var output = useCase.execute(command);

        assertNotNull(output);
        assertEquals(PaymentStatus.PENDING.toString(), output.paymentStatus());
    }

    @Test
    void givenInvalidCommand_whenGetPaymentStatusByExternalReferenceUseCase_thenShouldThrowNotFoundException() {
        final var command = new GetPaymentStatusByExternalReferenceCommand("123456789");

        assertThrows(NotFoundException.class, () -> useCase.execute(command));
    }
}