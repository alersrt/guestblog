package org.student.guestblog.usecases;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.student.guestblog.AbstractIntegrationTest;
import org.student.guestblog.rest.dto.user.UserResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Check working of the authentication configuration.
 */
@DisplayName("Authentication infrastructure test")
public class AuthTest extends AbstractIntegrationTest {

    @DisplayName("login: successful")
    @Test
    void obtainToken_positive() {
        /*------ Arranges ------*/
        String username = "user@test.dev";
        String password = "password";

        /*------ Actions ------*/
        var authCookie = getUserAuthorization(username, password);

        var currentUserResponse = client.toBlocking().exchange(
            HttpRequest.GET("/api/account/me")
                .header("Cookie", authCookie),
            UserResponse.class
        );
        UserResponse meDto = currentUserResponse.body();

        /*------ Asserts ------*/
        assertAll("check response",
            () -> assertThat(meDto.id()).isNotEmpty(),
            () -> assertThat(meDto.email()).isNotBlank().isEqualTo(username)
        );
    }

    @DisplayName("login: unsuccessful")
    @Test
    void obtainToken_negative() {
        /*------ Arranges ------*/
        String username = "user@test.dev";
        String password = "wrong_password";

        /*------ Actions ------*/
        var request = HttpRequest.POST(
                "/api/auth/login",
                new UsernamePasswordCredentials(username, password)
            )
            .contentType(MediaType.APPLICATION_JSON_TYPE);

        /*------ Asserts ------*/
        var exception = assertThrows(HttpClientResponseException.class,
            () -> client.toBlocking().exchange(request));
        assertThat(exception.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }
}
