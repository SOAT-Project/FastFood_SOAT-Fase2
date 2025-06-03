package soat.project.fastfoodsoat.application.usecase.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.adapter.outbound.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.ClientRepository;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientCommand;
import soat.project.fastfoodsoat.application.usecase.client.auth.DefaultAuthClientUseCase;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.utils.InstantUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@Transactional
@Rollback
public class IdentifyClientUseCaseIT {

    @Autowired
    private DefaultAuthClientUseCase useCase;

    @Autowired
    private ClientRepository clientRepository;

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
    void cleanUp() {
        clientRepository.deleteAll();
    }

    @Test
    void givenValidCpf_whenExecute_thenReturnsClientData() {
        // Arrange
        final var cpf = "98765432100";
        final var client = clientRepository.save(
                new ClientJpaEntity(
                        null,
                        UUID.randomUUID(),
                        "Carlos Alberto",
                        "carlos@email.com",
                        cpf,
                        InstantUtils.now(),
                        InstantUtils.now(),
                        null
                )
        );

        final var command = new AuthClientCommand(cpf);

        // Act
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        assertEquals(client.getPublicId(), output.publicId());
        assertEquals(client.getName(), output.name());
        assertEquals(client.getEmail(), output.email());
        assertEquals(client.getCpf(), output.cpf());
    }

    @Test
    void givenInvalidCpf_whenExecute_thenThrowsNotFoundException() {
        final var command = new AuthClientCommand("00000000000");

        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        assertEquals("client not found", exception.getMessage());
    }

    @Test
    void givenBlankIdentification_whenExecute_thenThrowsDomainException() {
        final var command = new AuthClientCommand("   ");

        final var exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("invalid identification", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullIdentification_whenExecute_thenThrowsDomainException() {
        final var command = new AuthClientCommand(null);

        final var exception = assertThrows(DomainException.class, () -> useCase.execute(command));

        assertEquals("invalid identification", exception.getErrors().get(0).message());
    }
}

