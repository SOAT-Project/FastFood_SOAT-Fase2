package soat.project.fastfoodsoat.adapter.outbound.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import soat.project.fastfoodsoat.domain.token.TokenService;
import soat.project.fastfoodsoat.domain.token.Token;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Component
public class JwtTokenService implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private static final String CLAIM_ROLES = "roles";

    @Override
    public Token generateToken(final String email, final List<String> roles, final Duration duration) {
        final Date now = new Date();
        final Date expiration = calculateExpiration(duration);
        final var jwt = Jwts.builder()
                .subject(email)
                .claim(CLAIM_ROLES, roles)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(SECRET_KEY)
                .compact();

        return new Token(jwt, "Bearer", duration.getSeconds(), roles);
    }

    private Date calculateExpiration(final Duration duration) {
        return Date.from(Instant.now().plus(duration));
    }

    @Override
    public boolean validateToken(String token) {
        try {
            final Jws<Claims> claims = parseToken(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (final Exception e) {
            log.debug(e.getMessage());
            return false;
        }
    }

    @Override
    public String extractEmail(final String token) {
        return parseToken(token).getPayload().getSubject();
    }

    @Override
    public List<String> extractRoles(final String token) {
        try {
            final Object rolesRaw = parseToken(token).getPayload().get(CLAIM_ROLES);

            if (Objects.isNull(rolesRaw)) return List.of();

            @SuppressWarnings("unchecked")
            final List<String> roles = (List<String>) rolesRaw;

            return roles;
        } catch (final ClassCastException ex) {
            log.debug(ex.getMessage());
            return List.of();
        }
    }

    private Jws<Claims> parseToken(final String token) {
        return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
    }

}
