package soat.project.fastfoodsoat.application.usecase.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.entity.ClientJpaEntity;
import soat.project.fastfoodsoat.infrastructure.persistence.jpa.repository.ClientRepository;
import soat.project.fastfoodsoat.application.command.client.create.CreateClientCommand;
import soat.project.fastfoodsoat.application.usecase.client.create.CreateClientUseCaseImpl;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.exception.ConflictException;
import soat.project.fastfoodsoat.shared.utils.InstantUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class CreateClientUseCaseIT {

    @Autowired
    private CreateClientUseCaseImpl useCase;

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
    @Transactional
    @Rollback
    public void givenValidData_whenExecute_thenCreatesClientSuccessfully() {

        final var name = "João Silva";
        final var email = "joao@email.com";
        final var cpf = "12345678902";

        final var command = CreateClientCommand.with(name, email, cpf);


        final var output = useCase.execute(command);


        assertNotNull(output);
        assertNotNull(output.publicId());
        assertEquals(name, output.name());
        assertEquals(email, output.email());
        assertEquals(cpf, output.CPF());

        final var clientOptional = clientRepository.findByCpf(cpf);
        assertTrue(clientOptional.isPresent());

        final var client = clientOptional.get();
        assertEquals(name, client.getName());
        assertEquals(email, client.getEmail());
        assertEquals(cpf, client.getCpf());
    }

    @Test
    @Transactional
    @Rollback
    public void givenExistingCpf_whenExecute_thenThrowsConflictException() {

        final var cpf = "12345678901";

        clientRepository.save(new ClientJpaEntity(
                null,
                UUID.randomUUID(),
                "Maria Souza",
                "maria@email.com",
                cpf,
                InstantUtils.now(),
                InstantUtils.now(),
                null
        ));

        final var command = CreateClientCommand.with("Maria Souza", "maria@email.com", cpf);


        final var exception = assertThrows(ConflictException.class, () -> useCase.execute(command));

        assertTrue(exception.getMessage().contains(Client.class.getSimpleName().toLowerCase()));
        assertTrue(exception.getMessage().contains(cpf));
    }
}
