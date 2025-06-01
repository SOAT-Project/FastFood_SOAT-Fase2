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
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.PaymentJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.OrderJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.PaymentRepository;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.OrderRepository;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.GetQRCodeCommand;
import soat.project.fastfoodsoat.application.usecase.payment.retrieve.get.qrcode.DefaultGetQRCodeUseCase;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.order.OrderStatus;
import soat.project.fastfoodsoat.domain.payment.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class GetQRCodeUseCaseIT {

    @Autowired
    private DefaultGetQRCodeUseCase useCase;

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
    void givenValidOrder_whenGetQRCode_thenShouldReturnQRCode() {
        OrderJpaEntity order = new OrderJpaEntity();
        order.setPublicId(UUID.randomUUID());
        order.setStatus(OrderStatus.RECEIVED);
        order.setValue(BigDecimal.valueOf(100.00));
        order.setOrderNumber(1);
        OrderJpaEntity savedOrder = orderRepository.save(order);

        PaymentJpaEntity payment = new PaymentJpaEntity();
        payment.setOrder(savedOrder);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setQrCode("QRCodeData");
        payment.setValue(BigDecimal.valueOf(100.00));
        payment.setExternalReference("ext-ref-" + UUID.randomUUID());
        paymentRepository.save(payment);

        GetQRCodeCommand command = new GetQRCodeCommand(payment.getExternalReference());
        String qrCode = useCase.execute(command);

        assertNotNull(qrCode);
    }

    @Test
    void givenInvalidExternalReference_whenGetQRCode_thenShouldThrowNotFoundException() {
        GetQRCodeCommand command = new GetQRCodeCommand("invalid-ext-ref");
        final var ex = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        assertNotNull(ex.getMessage());
        assertEquals("Payment not found for externalReference: invalid-ext-ref", ex.getMessage());
    }
}