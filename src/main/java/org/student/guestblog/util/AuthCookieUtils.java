package org.student.guestblog.util;

import com.google.common.annotations.VisibleForTesting;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.util.Arrays;

/**
 * Set of utility methods for working with auth cookies.
 */
public class AuthCookieUtils {

    /**
     * Delete authentication cookie.
     *
     * @param cookieName the auth cookie name.
     * @return cleared cookie.
     */
    public static ResponseCookie clearCookie(String cookieName) {
        return ResponseCookie
            .from(cookieName, "deleted")
            .path("/")
            .maxAge(0)
            .build();
    }

    /**
     * Populate authentication cookie.
     *
     * @param cookieName the auth cookie name.
     * @param token      the authentication token.
     * @return populated cookie.
     */
    public static ResponseCookie setResponseCookie(String cookieName, String token, long maxAge) {
        return ResponseCookie
            .from(cookieName, token)
            .httpOnly(true)
            .secure(true)
            .maxAge(maxAge)
            .sameSite("none")
            .path("/")
            .build();
    }

    /**
     * Used in testing purposes.
     *
     * @param cookieName the name of the auth cookie.
     * @param token      the auth token.
     * @return request auth cookie.
     */
    @VisibleForTesting
    public static Cookie setRequestCookie(String cookieName, String token) {
        var cookie = new Cookie(cookieName, token);
        return cookie;
    }

    /**
     * Extract authentication token from auth cookie.
     *
     * @param cookieName the auth cookie name.
     * @param request    incoming HTTP request.
     * @return presented token or null value.
     */
    public static String extractToken(String cookieName, HttpServletRequest request) {
        if (request.getCookies() != null) {
            var tokenCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst();
            if (tokenCookie.isPresent()) {
                String token = tokenCookie.get().getValue();
                if (token != null && !token.isEmpty()) {
                    return token;
                }
            }
        }
        return null;
    }
}
