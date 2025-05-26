package soat.project.fastfoodsoat.application.usecase.staff.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import soat.project.fastfoodsoat.IntegrationTest;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.StaffJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.StaffRepository;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.staff.Staff;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class AuthStaffUseCaseIT {

    @Autowired
    private AuthStaffUseCase useCase;

    @Autowired
    private StaffRepository staffRepository;

    @BeforeEach
    void individualTestSetup() {
        staffRepository.deleteAll();
    }

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

    @Test
    void givenAValidCommandWithEmail_whenCallsAuthStaff_shouldReturnToken() {
        // Given
        assertEquals(0, staffRepository.count());

        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());

        staffRepository.save(StaffJpaMapper.toJpa(staff));

        assertEquals(1, staffRepository.count());

        final var command = new AuthStaffCommand(staff.getEmail());

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.accessToken());
        assertNotNull(actualOutput.tokenType());
        assertNotNull(actualOutput.expiresIn());
    }

    @Test
    void givenAValidCommandWithCpf_whenCallsAuthStaff_shouldReturnToken() {
        // Given
        assertEquals(0, staffRepository.count());

        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());

        staffRepository.save(StaffJpaMapper.toJpa(staff));

        assertEquals(1, staffRepository.count());


        final var command = new AuthStaffCommand(staff.getCpf().getValue());

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.accessToken());
        assertNotNull(actualOutput.tokenType());
        assertNotNull(actualOutput.expiresIn());
    }

    @Test
    void givenACommandoWithInvalidStaff_whenCallsAuthStaff_shouldReceiveError() {
        // Given
        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "staff not found";

        final var command = new AuthStaffCommand(staff.getCpf().getValue());

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    void givenACommandoWithInvalidIdentification_whenCallsAuthStaff_shouldReceiveError() {
        // Given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "invalid identification";

        final var command = new AuthStaffCommand(null);

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

}
