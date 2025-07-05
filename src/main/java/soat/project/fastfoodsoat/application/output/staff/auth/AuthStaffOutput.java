package soat.project.fastfoodsoat.application.output.staff.auth;

public record AuthStaffOutput(String accessToken, String tokenType, Long expiresIn) {
}
