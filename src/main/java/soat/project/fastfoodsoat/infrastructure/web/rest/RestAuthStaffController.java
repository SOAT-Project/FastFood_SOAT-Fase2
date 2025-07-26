package soat.project.fastfoodsoat.infrastructure.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import soat.project.fastfoodsoat.infrastructure.auth.controller.AuthStaffController;
import soat.project.fastfoodsoat.infrastructure.web.rest.api.AuthStaffAPI;
import soat.project.fastfoodsoat.infrastructure.auth.model.request.AuthStaffRequest;
import soat.project.fastfoodsoat.infrastructure.auth.model.response.AuthStaffResponse;

@RestController
public class RestAuthStaffController implements AuthStaffAPI {

    private final AuthStaffController authStaffFacade;

    public RestAuthStaffController(final AuthStaffController authStaffFacade) {
        this.authStaffFacade = authStaffFacade;
    }

    @Override
    public ResponseEntity<AuthStaffResponse> authenticate(final AuthStaffRequest authRequest) {
        return ResponseEntity.ok(this.authStaffFacade.authenticate(authRequest));
    }
}
