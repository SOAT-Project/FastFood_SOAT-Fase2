package soat.project.fastfoodsoat.domain.client;

import org.junit.jupiter.api.Test;
import soat.project.fastfoodsoat.domain.exception.NotificationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void givenValidPramsAndCpfWithoutMask_whenClassNewClient_thenInstantiateClient() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        // When
        final var actualClient = Client.newClient(publicId, expectedName, expectedEmail, expectedCpf);

        // Then
        assertNotNull(actualClient);
        assertNull(actualClient.getId());
        assertEquals(publicId, actualClient.getPublicId());
        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(expectedCpf, actualClient.getCpf().getValue());
        assertNotNull(actualClient.getCreatedAt());
        assertNotNull(actualClient.getUpdatedAt());
        assertEquals(actualClient.getCreatedAt(), actualClient.getUpdatedAt());
        assertNull(actualClient.getDeletedAt());
    }

    @Test
    void givenValidPramsAndCpfWithMask_whenClassNewClient_thenInstantiateClient() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "123.456.789-01";
        final var publicId = UUID.randomUUID();

        // When
        final var actualClient = Client.newClient(publicId, expectedName, expectedEmail, expectedCpf);

        // Then

        final var cpfWithoutMask = expectedCpf.replaceAll("[.-]", "");
        assertNotNull(actualClient);
        assertNull(actualClient.getId());
        assertEquals(publicId, actualClient.getPublicId());
        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(cpfWithoutMask, actualClient.getCpf().getValue());
        assertNotNull(actualClient.getCreatedAt());
        assertNotNull(actualClient.getUpdatedAt());
        assertEquals(actualClient.getCreatedAt(), actualClient.getUpdatedAt());
        assertNull(actualClient.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenClassNewClient_shouldReceiveError() {
        // Given
        final String expectedName = null;
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenClassNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestName_whenClassNewClient_shouldReceiveError() {
        // Given
        final var expectedName = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore
                """;
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestName_whenClassNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "A";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }


    @Test
    void givenInvalidNullEmail_whenClassNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final String expectedEmail = null;
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'email' should not be null";
        final var expectedErrorCount = 1;

        // Then
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyEmail_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'email' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidMalformedEmail_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe.com";
        final var expectedCpf = "12345678901";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'email' format is not valid";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullCpf_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = null;
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'cpf' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId,expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyCpf_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'cpf' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestCpf_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "123456789123";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'cpf' size must be 11 digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestCpf_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "123456789";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'cpf' size must be 11 digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidMalformedCpf_whenCallsNewClient_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "ABCDEFGHIJK";
        final var publicId = UUID.randomUUID();

        final var expectedErrorMessage = "'cpf' should contain only digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Client.newClient(publicId, expectedName, expectedEmail, expectedCpf)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

}