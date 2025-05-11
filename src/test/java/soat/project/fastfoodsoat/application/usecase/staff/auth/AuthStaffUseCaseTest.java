package soat.project.fastfoodsoat.application.usecase.staff.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffGateway;
import soat.project.fastfoodsoat.domain.token.Token;
import soat.project.fastfoodsoat.domain.token.TokenService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthStaffUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAuthStaffUseCase useCase;

    @Mock
    private StaffGateway staffGateway;

    @Mock
    private TokenService tokenService;


    @Override
    protected List<Object> getMocks() {
        return List.of(staffGateway, tokenService);
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(useCase, "tokenExpiration", 43200L);
    }

    @Test
    void givenAValidCommandWithEmail_whenCallsAuthStaff_shouldReturnToken() {
        // Given
        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());
        final var token = new Token("token", "Bearer", 43200L, List.of());

        final var command = new AuthStaffCommand(staff.getEmail());

        when(staffGateway.findByEmail(anyString())).thenReturn(Optional.of(staff));

        when(tokenService.generateToken(any(), any(), any())).thenReturn(token);

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.accessToken());
        assertNotNull(actualOutput.tokenType());
        assertNotNull(actualOutput.expiresIn());

        verify(staffGateway, times(1)).findByEmail(anyString());
        verify(tokenService, times(1)).generateToken(any(), any(), any());
        Mockito.verifyNoMoreInteractions(staffGateway, tokenService);
    }

    @Test
    void givenAValidCommandWithCpf_whenCallsAuthStaff_shouldReturnToken() {
        // Given
        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());
        final var token = new Token("token", "Bearer", 43200L, List.of());

        final var command = new AuthStaffCommand(staff.getCpf().getValue());

        when(staffGateway.findByEmail(anyString())).thenReturn(Optional.empty());
        when(staffGateway.findByCpf(any())).thenReturn(Optional.of(staff));

        when(tokenService.generateToken(any(), any(), any())).thenReturn(token);

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.accessToken());
        assertNotNull(actualOutput.tokenType());
        assertNotNull(actualOutput.expiresIn());

        verify(staffGateway, times(1)).findByEmail(anyString());
        verify(staffGateway, times(1)).findByCpf(any());
        verify(tokenService, times(1)).generateToken(any(), any(), any());
        Mockito.verifyNoMoreInteractions(staffGateway, tokenService);
    }

    @Test
    void givenACommandoWithInvalidStaff_whenCallsAuthStaff_shouldReceiveError() {
        // Given
        final var staff = Staff.newStaff("john", "john@email.com", "12345678901", List.of());

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "staff not found";

        final var command = new AuthStaffCommand(staff.getCpf().getValue());

        when(staffGateway.findByEmail(anyString())).thenReturn(Optional.empty());
        when(staffGateway.findByCpf(any())).thenReturn(Optional.empty());

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(staffGateway, times(1)).findByEmail(anyString());
        verify(staffGateway, times(1)).findByCpf(any());
        Mockito.verifyNoMoreInteractions(staffGateway, tokenService);
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

        Mockito.verifyNoMoreInteractions(staffGateway, tokenService);
    }

}