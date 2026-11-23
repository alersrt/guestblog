package org.student.guestblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Tag;
import org.student.guestblog.util.Cookie;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.Map;

@MicronautTest
@Tag("integration")
public abstract class AbstractIntegrationTest implements TestPropertyProvider {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.3-alpine"))
            .withDatabaseName("gbdb")
            .withUsername("postgres")
            .withPassword("postgres");

    /**
     * The main testing tool.
     */
    @Inject
    @Client("/")
    protected HttpClient client;

    /**
     * Needs for the mapping of the responses.
     */
    @Inject
    protected ObjectMapper objectMapper;

    @Override
    public Map<String, String> getProperties() {
        return testProperties();
    }

    static Map<String, String> testProperties() {
        var postgres = postgres();
        return Map.of(
                "datasources.default.url", postgres.getJdbcUrl(),
                "datasources.default.username", "gb_user_rw",
                "datasources.default.password", "gb_user_rw",
                "micronaut.security.token.cookie.cookie-secure", "false",
                "micronaut.security.redirect.enabled", "false",
                "micronaut.security.oauth2.clients.google.enabled", "false",
                "micronaut.security.oauth2.clients.github.enabled", "false",
                "micronaut.security.token.jwt.signatures.secret.generator.secret", "test-jwt-secret-test-jwt-secret-1234"
        );
    }

    /**
     * Retrieve user's authorization cookie.
     *
     * @param username user's e-mail.
     * @param password user's password.
     * @return authorization cookie.
     */
    protected String getUserAuthorization(String username, String password) {
        var form = "username=" + java.net.URLEncoder.encode(username, java.nio.charset.StandardCharsets.UTF_8)
               + "&password=" + java.net.URLEncoder.encode(password, java.nio.charset.StandardCharsets.UTF_8);
        try {
            var response = client.toBlocking().exchange(
                    HttpRequest.POST("/api/auth/login", form)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                    String.class
            );
            return extractAuthCookie(response);
        } catch (io.micronaut.http.client.exceptions.HttpClientResponseException e) {
            String body = e.getResponse().getBody(String.class).orElse("");
            throw new AssertionError("Login failed: " + e.getStatus() + " " + body, e);
        }
    }

    protected String extractAuthCookie(HttpResponse<?> response) {
        return response.getHeaders().getAll("Set-Cookie").stream()
                .flatMap(header -> Arrays.stream(header.split(";")))
                .map(String::trim)
                .filter(cookie -> cookie.startsWith(Cookie.X_AUTH_REMEMBER_ME + "="))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Authentication cookie is not present"));
    }

    private static PostgreSQLContainer<?> postgres() {
        if (!POSTGRES.isRunning()) {
            POSTGRES.start();
            Flyway.configure()
                    .dataSource(POSTGRES.getJdbcUrl(), "postgres", "postgres")
                    .locations("filesystem:db/changelog")
                    .load()
                    .migrate();
        }
        return POSTGRES;
    }
}
