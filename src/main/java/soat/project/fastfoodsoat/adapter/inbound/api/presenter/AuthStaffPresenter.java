package soat.project.fastfoodsoat.adapter.inbound.api.presenter;

import soat.project.fastfoodsoat.adapter.inbound.api.model.AuthStaffResponse;
import soat.project.fastfoodsoat.application.usecase.staff.auth.AuthStaffOutput;

public interface AuthStaffPresenter {

    static AuthStaffResponse present(final AuthStaffOutput output) {
        return new AuthStaffResponse(
                output.accessToken(),
                output.tokenType(),
                output.expiresIn()
        );
    }

}
