package cryptocats.backend.util;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CookieCreator {
    @Value("${cookie.token.name}")
    private String cookieToken;

    public Cookie createCookie(String name, String value, String path, boolean httpOnly, boolean setSecure,
                               Map<String, String> attributes) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(setSecure);
        for (Map.Entry<String, String> attr : attributes.entrySet()) {
            cookie.setAttribute(attr.getKey(), attr.getValue());
        }
        return cookie;
    }

    public Cookie createCookie(String name, String value, String path, boolean httpOnly, boolean setSecure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(setSecure);
        return cookie;
    }

    public Cookie createCookieToken(String token) {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Partitioned", "");
        attributes.put("SameSite", "None");
        return createCookie(cookieToken, token,
                "/", true, true, attributes);
    }
}
