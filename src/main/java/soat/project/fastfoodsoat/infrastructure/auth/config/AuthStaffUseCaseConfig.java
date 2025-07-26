package soat.project.fastfoodsoat.infrastructure.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soat.project.fastfoodsoat.application.gateway.StaffRepositoryGateway;
import soat.project.fastfoodsoat.application.gateway.TokenService;
import soat.project.fastfoodsoat.application.usecase.staff.auth.AuthStaffUseCaseImpl;

@Configuration
public class AuthStaffUseCaseConfig {

    @Value("${security.auth.token.expiration}")
    private Long tokenExpiration;

    private final StaffRepositoryGateway staffRepositoryGateway;

    private final TokenService tokenService;

    public AuthStaffUseCaseConfig(final StaffRepositoryGateway staffRepositoryGateway, TokenService tokenService) {
        this.staffRepositoryGateway = staffRepositoryGateway;
        this.tokenService = tokenService;
    }

    @Bean
    public AuthStaffUseCaseImpl authStaffUseCase() { return new AuthStaffUseCaseImpl(tokenExpiration, staffRepositoryGateway, tokenService);}
}
