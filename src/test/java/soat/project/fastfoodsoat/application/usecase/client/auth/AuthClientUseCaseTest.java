package soat.project.fastfoodsoat.application.usecase.client.auth;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.domain.client.ClientGateway;
import soat.project.fastfoodsoat.domain.client.ClientPublicId;
import soat.project.fastfoodsoat.domain.exception.DomainException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthClientUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultAuthClientUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(clientGateway);
    }

    @Test
    void givenValidCommandWithCPF_WhenCallAuthClient_shouldReturnClient() {
        // Given
        final var client = Client.newClient(ClientPublicId.of(UUID.randomUUID()), "john", "john@email.com", "12345678901");
        final var command = new AuthClientCommand(client.getCpf().getValue());

        when(clientGateway.findByCpf(client.getCpf())).thenReturn(Optional.of(client));

        // When
        final var actualOutput = useCase.execute(command);

        // Then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.publicId());
        assertNotNull(actualOutput.name());
        assertNotNull(actualOutput.email());
        assertNotNull(actualOutput.cpf());

        verify(clientGateway, times(1)).findByCpf(any());
        Mockito.verifyNoMoreInteractions(clientGateway);
    }

    @Test
    void givenACommandoWithInvalidIdentification_whenCallsAuthClient_shouldReceiveError() {
        // Given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "invalid identification";
        final var command = new AuthClientCommand(null);

        // When
        final var actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(command)
        );

        // Then
        assertNotNull(actualException);
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        Mockito.verifyNoMoreInteractions(clientGateway);
    }
}