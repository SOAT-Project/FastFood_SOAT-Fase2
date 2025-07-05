package soat.project.fastfoodsoat.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import soat.project.fastfoodsoat.application.gateway.TokenService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenService tokenService;

    public JwtAuthFilter(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            final @NonNull HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final var authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final var jwt = extractJwtToken(authHeader);
        final var email = tokenService.extractEmail(jwt);
        final var roles = tokenService.extractRoles(jwt);

        if (!canAuthenticate(email, roles)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!tokenService.validateToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        authenticate(email, roles, request);

        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(final String auth) {
        return auth.substring(BEARER_PREFIX.length());
    }

    private boolean canAuthenticate(final String email, final List<String> roles) {
        return Objects.nonNull(email)
                && !roles.isEmpty()
                && Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private void authenticate(final String email, final List<String> roles, final HttpServletRequest request) {
        final var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        final var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
