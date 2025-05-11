package soat.project.fastfoodsoat.domain.token;

import java.util.List;

public record Token(String value, String type, Long expires, List<String> roles) {
}
