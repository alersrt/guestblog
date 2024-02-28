package org.student.guestblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.student.guestblog.util.Cookie;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.lifecycle.Startables;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Tag("integration")
public abstract class AbstractIntegrationTest {

    /**
     * Environment.
     */
    protected static final DockerComposeContainer<?> ENVIRONMENT_TEST;

    private static final String POSTGRESQL_SERVICE = "postgresql";
    private static final int POSTGRESQL_PORT = 5432;
    private static final String HAZELCAST_SERVICE = "hazelcast";
    private static final int HAZELCAST_PORT = 5701;
    private static final String KAFKA_SERVICE = "kafka";
    private static final int KAFKA_PORT = 29092;

    static {
        ENVIRONMENT_TEST = new DockerComposeContainer<>(new File("./docker/docker-compose.env.yml"))
            .withExposedService(POSTGRESQL_SERVICE, POSTGRESQL_PORT)
            .withLocalCompose(true);

        Startables.deepStart(Stream.of(ENVIRONMENT_TEST)).join();

        String jdbcUrl = "jdbc:postgresql://%s:%s/gbdb".formatted(
            ENVIRONMENT_TEST.getServiceHost(POSTGRESQL_SERVICE, POSTGRESQL_PORT),
            ENVIRONMENT_TEST.getServicePort(POSTGRESQL_SERVICE, POSTGRESQL_PORT)
        );

        System.setProperty("GB_POSTGRES_URL", jdbcUrl);
        System.setProperty("GB_POSTGRES_USERNAME", "postgres");
        System.setProperty("GB_POSTGRES_PASSWORD", "postgres");
    }

    /**
     * The main testing tool.
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * Needs for the mapping of the responses.
     */
    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * Retrieve user authorization token
     *
     * @return authorization token.
     * @throws Exception if something went wrong.
     */
    protected jakarta.servlet.http.Cookie getUserAuthorization(@NotNull String username, @NotNull String password) throws Exception {
        String clientAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());
        ResultActions resultActions = mockMvc.perform(
            post("/api/auth/login")
                .param("username", username)
                .param("password", password)
        );

        var authResponse = resultActions.andReturn().getResponse();

        return Arrays.stream(authResponse.getCookies())
            .filter(cookie -> cookie.getName().equals(Cookie.X_AUTH_REMEMBER_ME))
            .findFirst()
            .get();
    }

    protected jakarta.servlet.http.Cookie prolongAuthCookie(MockHttpServletResponse response) {
        return Arrays.stream(response.getCookies())
            .filter(cookie -> cookie.getName().equals(Cookie.X_AUTH_REMEMBER_ME))
            .findFirst()
            .get();
    }
}
