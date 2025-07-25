package soat.project.fastfoodsoat.infrastructure.auth.controller;

import soat.project.fastfoodsoat.infrastructure.auth.model.request.AuthStaffRequest;
import soat.project.fastfoodsoat.infrastructure.auth.model.response.AuthStaffResponse;

public interface AuthStaffController {
    AuthStaffResponse authenticate(AuthStaffRequest request);
}
