package soat.project.fastfoodsoat.application.usecase.staff.auth;

public record AuthStaffOutput(String accessToken, String tokenType, Long expiresIn) {
}
