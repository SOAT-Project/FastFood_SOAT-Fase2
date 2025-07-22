package soat.project.fastfoodsoat.infrastructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soat.project.fastfoodsoat.application.gateway.ClientRepositoryGateway;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientUseCase;
import soat.project.fastfoodsoat.application.usecase.client.auth.AuthClientUseCaseImpl;
import soat.project.fastfoodsoat.application.usecase.client.create.CreateClientUseCase;
import soat.project.fastfoodsoat.application.usecase.client.create.CreateClientUseCaseImpl;

@Configuration
public class ClientUseCaseConfig {

    private final ClientRepositoryGateway clientRepositoryGateway;

    public ClientUseCaseConfig(final ClientRepositoryGateway clientRepositoryGateway) {
        this.clientRepositoryGateway = clientRepositoryGateway;
    }

    @Bean
    public AuthClientUseCase authClientUseCase() {
        return new AuthClientUseCaseImpl(clientRepositoryGateway);
    }

    @Bean
    public CreateClientUseCase createClientUseCase() {
        return new CreateClientUseCaseImpl(clientRepositoryGateway);
    }

}
