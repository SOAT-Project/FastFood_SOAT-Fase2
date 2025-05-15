package soat.project.fastfoodsoat.application.usecase.staff.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import soat.project.fastfoodsoat.adapter.outbound.jpa.mapper.StaffJpaMapper;
import soat.project.fastfoodsoat.adapter.outbound.jpa.repository.StaffRepository;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.setup.BaseIntegrationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
public class AuthStaffUseCaseIT extends BaseIntegrationTest {

    @Autowired
    private AuthStaffUseCase useCase;

    @Autowired
    private StaffRepository staffRepository;

    @BeforeEach
    void individualTestSetup() {
        staffRepository.deleteAll();
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
