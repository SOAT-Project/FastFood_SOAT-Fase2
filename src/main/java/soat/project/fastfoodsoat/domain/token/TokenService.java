package soat.project.fastfoodsoat.domain.token;

import java.time.Duration;
import java.util.List;

public interface TokenService {
    Token generateToken(String email, List<String> roles, Duration duration);
    boolean validateToken(String token);
    String extractEmail(String token);
    List<String> extractRoles(final String token);
}
