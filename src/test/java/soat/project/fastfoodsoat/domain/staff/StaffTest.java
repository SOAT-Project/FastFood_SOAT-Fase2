package soat.project.fastfoodsoat.domain.staff;

import org.junit.jupiter.api.Test;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.role.Role;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest {

    @Test
    void givenValidPramsAndCpfWithoutMask_whenClassNewStaff_thenInstantiateStaff() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        // When
        final var actualStaff = Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles);

        // Then
        assertNotNull(actualStaff);
        assertNull(actualStaff.getId());
        assertEquals(expectedName, actualStaff.getName());
        assertEquals(expectedEmail, actualStaff.getEmail());
        assertEquals(expectedCpf, actualStaff.getCpf().getValue());
        assertEquals(expectedRoles.size(), actualStaff.getRoles().size());
        assertNotNull(actualStaff.getCreatedAt());
        assertNotNull(actualStaff.getUpdatedAt());
        assertEquals(actualStaff.getCreatedAt(), actualStaff.getUpdatedAt());
        assertNull(actualStaff.getDeletedAt());
    }

    @Test
    void givenValidPramsAndCpfWithMask_whenClassNewStaff_thenInstantiateStaff() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "123.456.789-01";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        // When
        final var actualStaff = Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles);

        // Then

        final var cpfWithoutMask = expectedCpf.replaceAll("[.-]", "");
        assertNotNull(actualStaff);
        assertNull(actualStaff.getId());
        assertEquals(expectedName, actualStaff.getName());
        assertEquals(expectedEmail, actualStaff.getEmail());
        assertEquals(cpfWithoutMask, actualStaff.getCpf().getValue());
        assertEquals(expectedRoles.size(), actualStaff.getRoles().size());
        assertNotNull(actualStaff.getCreatedAt());
        assertNotNull(actualStaff.getUpdatedAt());
        assertEquals(actualStaff.getCreatedAt(), actualStaff.getUpdatedAt());
        assertNull(actualStaff.getDeletedAt());
    }

    @Test
    void givenInvalidNullName_whenClassNewStaff_shouldReceiveError() {
        // Given
        final String expectedName = null;
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyName_whenClassNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestName_whenClassNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore
                """;
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestName_whenClassNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "A";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'name' must be between 3 and 100 characters";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }


    @Test
    void givenInvalidNullEmail_whenClassNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final String expectedEmail = null;
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'email' should not be null";
        final var expectedErrorCount = 1;

        // Then
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyEmail_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'email' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidMalformedEmail_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe.com";
        final var expectedCpf = "12345678901";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'email' format is not valid";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidNullCpf_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = null;
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'cpf' should not be null";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidEmptyCpf_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'cpf' should not be empty";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidBiggestCpf_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "123456789123";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'cpf' size must be 11 digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidSmallestCpf_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "123456789";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'cpf' size must be 11 digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    void givenInvalidMalformedCpf_whenCallsNewStaff_shouldReceiveError() {
        // Given
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final String expectedCpf = "ABCDEFGHIJK";
        final var expectedRoles = List.of(Role.newRole("ADMIN"), Role.newRole("EMPLOYEE"));

        final var expectedErrorMessage = "'cpf' should contain only digits";
        final var expectedErrorCount = 1;

        // When
        final var actualException = assertThrows(
                NotificationException.class,
                () -> Staff.newStaff(expectedName, expectedEmail, expectedCpf, expectedRoles)
        );

        // Then
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

}