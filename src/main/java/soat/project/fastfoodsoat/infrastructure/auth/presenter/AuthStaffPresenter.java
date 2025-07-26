package soat.project.fastfoodsoat.infrastructure.auth.presenter;

import soat.project.fastfoodsoat.infrastructure.auth.model.response.AuthStaffResponse;
import soat.project.fastfoodsoat.application.output.staff.auth.AuthStaffOutput;

public interface AuthStaffPresenter {

    static AuthStaffResponse present(final AuthStaffOutput output) {
        return new AuthStaffResponse(
                output.accessToken(),
                output.tokenType(),
                output.expiresIn()
        );
    }

}
