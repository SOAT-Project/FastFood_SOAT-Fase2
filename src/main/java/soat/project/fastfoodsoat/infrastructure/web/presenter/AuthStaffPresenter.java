package soat.project.fastfoodsoat.infrastructure.web.presenter;

import soat.project.fastfoodsoat.infrastructure.web.model.response.auth.AuthStaffResponse;
import soat.project.fastfoodsoat.application.output.staff.AuthStaffOutput;

public interface AuthStaffPresenter {

    static AuthStaffResponse present(final AuthStaffOutput output) {
        return new AuthStaffResponse(
                output.accessToken(),
                output.tokenType(),
                output.expiresIn()
        );
    }

}
