package cryptocats.backend.config;

import cryptocats.backend.config.JwtService;
import cryptocats.backend.util.CookieCreator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final CookieCreator cookieCreator;

    @Value("${cookie.token.name}")
    private String name;

    @Override
    public void logout(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull Authentication authentication
    ) {
        if (request.getCookies() == null) {
            return;
        }
        Optional<Cookie> cookieToken = Arrays.stream(request.getCookies())
                .filter(cookie ->  cookie.getName().equals(name))
                .findFirst();
        if (cookieToken.isEmpty()) {
            return;
        }
        final String jwt = cookieToken.get().getValue();
        if (jwt == null || jwt.isEmpty()) {
            return;
        }
        response.addCookie(cookieCreator.createCookieToken(null));
        SecurityContextHolder.clearContext();
    }
}