package soat.project.fastfoodsoat.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.web.controller.api.AuthStaffAPI;
import soat.project.fastfoodsoat.infrastructure.web.model.request.auth.AuthStaffRequest;
import soat.project.fastfoodsoat.infrastructure.web.model.response.auth.AuthStaffResponse;
import soat.project.fastfoodsoat.infrastructure.web.presenter.AuthStaffPresenter;
import soat.project.fastfoodsoat.application.command.staff.auth.AuthStaffCommand;
import soat.project.fastfoodsoat.application.usecase.staff.auth.AuthStaffUseCase;

@RestController
public class AuthStaffController implements AuthStaffAPI {

    private final AuthStaffUseCase authStaffUseCase;

    public AuthStaffController(final AuthStaffUseCase authStaffUseCase) {
        this.authStaffUseCase = authStaffUseCase;
    }

    @Override
    public ResponseEntity<AuthStaffResponse> authenticate(final AuthStaffRequest authRequest) {
        final var command = new AuthStaffCommand(authRequest.identification());

        final var output = this.authStaffUseCase.execute(command);

        return ResponseEntity.ok(AuthStaffPresenter.present(output));
    }

}
