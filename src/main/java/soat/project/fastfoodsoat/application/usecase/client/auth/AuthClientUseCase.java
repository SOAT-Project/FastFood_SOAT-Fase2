package soat.project.fastfoodsoat.application.usecase.client.auth;

import soat.project.fastfoodsoat.application.usecase.UseCase;
import soat.project.fastfoodsoat.application.command.client.auth.AuthClientCommand;
import soat.project.fastfoodsoat.application.output.client.auth.AuthClientOutput;

public abstract class AuthClientUseCase extends UseCase<AuthClientCommand, AuthClientOutput> {
}
