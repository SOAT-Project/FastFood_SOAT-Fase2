package soat.project.fastfoodsoat.application.usecase.client.create;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import soat.project.fastfoodsoat.application.command.client.CreateClientCommand;
import soat.project.fastfoodsoat.application.usecase.UseCaseTest;
import soat.project.fastfoodsoat.domain.client.Client;
import soat.project.fastfoodsoat.application.gateway.ClientRepositoryGateway;
import soat.project.fastfoodsoat.domain.client.ClientId;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CreateClientUseCaseTest extends UseCaseTest {

    @InjectMocks
    private CreateClientUseCaseImpl useCase;

    @Mock
    private ClientRepositoryGateway clientRepositoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(clientRepositoryGateway);
    }

    @Test
    void givenValidCommand_whenCreateClient_thenShouldReturnClientId() {
        // Given
        final var name = "John Doe";
        final var email = "johndoe@example.com";
        final var cpf = "12345678901";
        final var command = new CreateClientCommand(name, email, cpf);

        when(clientRepositoryGateway.findByCpf(any())).thenReturn(Optional.empty());
        when(clientRepositoryGateway.create(any())).thenAnswer(invocation -> {
            Client client = invocation.getArgument(0);
            ReflectionTestUtils.setField(client, "id", ClientId.of(1));
            return client;
        });

        // When
        final var output = useCase.execute(command);

        // Then
        assertNotNull(output);
        assertEquals(name, output.name());
        assertEquals(email, output.email());
        assertEquals(cpf, output.CPF());
        verify(clientRepositoryGateway, times(1)).create(any());
    }
}