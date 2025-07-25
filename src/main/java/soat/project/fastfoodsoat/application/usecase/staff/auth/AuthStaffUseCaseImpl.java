package soat.project.fastfoodsoat.application.usecase.staff.auth;

import soat.project.fastfoodsoat.application.command.staff.auth.AuthStaffCommand;
import soat.project.fastfoodsoat.application.output.staff.auth.AuthStaffOutput;
import soat.project.fastfoodsoat.domain.exception.DomainException;
import soat.project.fastfoodsoat.domain.exception.NotFoundException;
import soat.project.fastfoodsoat.domain.staff.Staff;
import soat.project.fastfoodsoat.domain.staff.StaffCpf;
import soat.project.fastfoodsoat.application.gateway.StaffRepositoryGateway;
import soat.project.fastfoodsoat.domain.role.Role;
import soat.project.fastfoodsoat.domain.validation.DomainError;
import soat.project.fastfoodsoat.application.gateway.TokenService;
import soat.project.fastfoodsoat.domain.token.Token;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class AuthStaffUseCaseImpl extends AuthStaffUseCase {

    private final Long tokenExpiration;
    private final StaffRepositoryGateway staffRepositoryGateway;
    private final TokenService tokenService;

    public AuthStaffUseCaseImpl(
            final Long tokenExpiration,
            final StaffRepositoryGateway staffRepositoryGateway,
            final TokenService tokenService
    ) {
        this.tokenExpiration = tokenExpiration;
        this.staffRepositoryGateway = staffRepositoryGateway;
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
        var staff = this.staffRepositoryGateway.findByEmail(identification);

        if (staff.isPresent()) return staff.get();

        staff = this.staffRepositoryGateway.findByCpf(StaffCpf.of(identification));

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
