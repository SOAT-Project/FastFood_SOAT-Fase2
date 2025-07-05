package soat.project.fastfoodsoat.domain.staff.role;

import org.junit.jupiter.api.Test;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.role.Role;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void givenValidPrams_whenClassNewRole_thenInstantiateRole() {
        // Given
        final var expectedName = "ADMIN";

        // When
        final var actualRole = Role.newRole(expectedName);

        // Then
        assertNotNull(actualRole);
        assertNull(actualRole.getId());
        assertEquals(expectedName, actualRole.getName());
        assertNotNull(actualRole.getCreatedAt());
        assertNotNull(actualRole.getUpdatedAt());
        assertEquals(actualRole.getCreatedAt(), actualRole.getUpdatedAt());
        assertNull(actualRole.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenClassNewRole_shouldReceiveError() {
        // Given
        final String expectedName = null;

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Role.newRole(expectedName)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenClassNewRole_shouldReceiveError() {
        // Given
        final var expectedName = "";

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Role.newRole(expectedName)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestName_whenClassNewRole_shouldReceiveError() {
        // Given
        final var expectedName = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore
                """;

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Role.newRole(expectedName)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestName_whenClassNewRole_shouldReceiveError() {
        // Given
        final var expectedName = "AA";

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Role.newRole(expectedName)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

}