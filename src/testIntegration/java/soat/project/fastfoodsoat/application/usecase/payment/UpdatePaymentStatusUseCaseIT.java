package soat.project.fastfoodsoat.application.usecase.payment;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.application.command.payment.update.UpdatePaymentStatusCommand;
import soat.project.fastfoodsoat.application.usecase.payment.update.status.UpdatePaymentStatusUseCaseImpl;
import soat.project.fastfoodsoat.domain.exception.IllegalStateException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.PaymentJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class UpdatePaymentStatusUseCaseIT {

    @Autowired
    private UpdatePaymentStatusUseCaseImpl useCase;

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
    void givenValidCommand_whenUpdatePaymentStatusUseCase_thenShouldUpdatePaymentStatus() {
        // Given
        OrderJpaEntity order = new OrderJpaEntity();
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.RECEIVED);
        order.setValue(BigDecimal.valueOf(100.00));
        order.setOrderNumber(1);
        OrderJpaEntity savedOrder = orderRepository.save(order);

        PaymentJpaEntity payment = new PaymentJpaEntity();
        payment.setOrder(savedOrder);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setQrCode("someQRCode");
        payment.setValue(BigDecimal.valueOf(100.00));
        payment.setExternalReference("ext-ref-123");
        paymentRepository.save(payment);

        final var command = new UpdatePaymentStatusCommand("ext-ref-123", "APPROVED");

        // When
        final var output = useCase.execute(command);

        // Then
        assertNotNull(output);
        assertEquals("ext-ref-123", output.externalReference());
        assertEquals(PaymentStatus.APPROVED, output.newPaymentStatus());

        PaymentJpaEntity updatedPayment = paymentRepository.findByExternalReference("ext-ref-123").orElse(null);
        assertNotNull(updatedPayment);
        assertEquals(PaymentStatus.APPROVED, updatedPayment.getStatus());
    }

    @Test
    @Transactional
    void givenInvalidCommand_whenUpdatePaymentStatusUseCase_thenShouldThrowNotFoundException() {
        // Given
        final var command = new UpdatePaymentStatusCommand("non-existent-ref", "APPROVED");

        // When/Then
        assertThrows(NotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    @Transactional
    void givenInvalidStatus_whenUpdatePaymentStatusUseCase_thenShouldThrowIllegalStateException() {
        // Given
        OrderJpaEntity order = new OrderJpaEntity();
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.RECEIVED);
        order.setValue(BigDecimal.valueOf(100.00));
        order.setOrderNumber(3);
        OrderJpaEntity savedOrder = orderRepository.save(order);

        PaymentJpaEntity payment = new PaymentJpaEntity();
        payment.setOrder(savedOrder);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setQrCode("someQRCode3");
        payment.setValue(BigDecimal.valueOf(100.00));
        payment.setExternalReference("ext-ref-789");
        paymentRepository.save(payment);

        final var command = new UpdatePaymentStatusCommand("ext-ref-789", "INVALID_STATUS");

        // When/Then
        assertThrows(IllegalStateException.class, () -> useCase.execute(command));
    }
}