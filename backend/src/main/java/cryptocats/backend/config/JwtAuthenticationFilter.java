package cryptocats.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Value("${cookie.token.name}")
    private String cookieName;

    private void authenticateUser(@NotNull HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<Cookie> getAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findAny();
        }

        return Optional.empty();
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> authCookie = getAuthCookie(request);

        if (authCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authCookie.get().getValue();
        if (jwt.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.tokenValidator(jwt, userDetails)) {
                authenticateUser(request, userDetails);
            }
        }

        filterChain.doFilter(request, response);
    }
}