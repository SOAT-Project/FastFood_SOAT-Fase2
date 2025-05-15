package soat.project.fastfoodsoat.application.usecase.staff.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.domain.staff.StaffGateway;
import soat.project.fastfoodsoat.domain.staff.role.Role;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.domain.token.TokenService;
import soat.project.fastfoodsoat.domain.token.Token;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
public class DefaultAuthStaffUseCase extends AuthStaffUseCase {

    @Value("${security.auth.token.expiration}")
    private Long tokenExpiration;

    private final StaffGateway staffGateway;
    private final TokenService tokenService;

    public DefaultAuthStaffUseCase(
            final StaffGateway staffGateway,
            final TokenService tokenService
    ) {
        this.staffGateway = staffGateway;
        this.tokenService = tokenService;
    }

    @Override
    public AuthStaffOutput execute(final AuthStaffCommand command) {
        final var identification = command.identification();

        if (Objects.isNull(identification) || identification.isBlank()) {
            throw DomainException.with(new DomainError("invalid identification"));
        }

        final var staff = retrieveStaff(identification);

        if (!staff.isActive() && Objects.nonNull(staff.getDeletedAt())) {
            throw DomainException.with(new DomainError("staff is inactive"));
        }

        final var token = generateToken(staff);

        return new AuthStaffOutput(token.value(), token.type(), token.expires());
    }

    private Staff retrieveStaff(final String identification) {
        var staff = this.staffGateway.findByEmail(identification);

        if (staff.isPresent()) return staff.get();

        staff = this.staffGateway.findByCpf(StaffCpf.of(identification));

        if (staff.isPresent()) return staff.get();

        throw NotFoundException.with(new DomainError("staff not found"));
    }

    private Token generateToken(final Staff staff) {
        final var email = staff.getEmail();
        final var roles = staff.getRoles().stream().map(Role::getName).toList();
        final var expires = Duration.of(tokenExpiration, ChronoUnit.SECONDS);
        return this.tokenService.generateToken(email, roles, expires);
    }

}
