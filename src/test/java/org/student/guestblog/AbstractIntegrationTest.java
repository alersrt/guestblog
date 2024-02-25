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
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;
import java.time.Duration;
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
     * Container with the PostgreSQL database.
     */
    protected static final PostgreSQLContainer<?> DB;

    static {
        var network = Network.newNetwork();

        var dbImage = new ImageFromDockerfile("test-debt-court-db", false)
            .withFileFromPath(".", Path.of("./docker/postgres/.").toAbsolutePath());
        dbImage.get();
        DB = new PostgreSQLContainer<>(DockerImageName.parse(dbImage.getDockerImageName()).asCompatibleSubstituteFor("postgres"))
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withImagePullPolicy(PullPolicy.ageBased(Duration.ofDays(30)))
            .withNetwork(network)
            .withFileSystemBind(Path.of("./docker/postgres/postgresql.conf").toAbsolutePath().toString(), "/etc/postgresql/postgresql.conf")
            .withCommand("postgres", "-c", "max_prepared_transactions=100", "-c", "config_file=/etc/postgresql/postgresql.conf");

        Startables.deepStart(Stream.of(DB)).join();

        System.setProperty("GB_POSTGRES_URL", DB.getJdbcUrl());
        System.setProperty("GB_POSTGRES_USERNAME", DB.getUsername());
        System.setProperty("GB_POSTGRES_PASSWORD", DB.getPassword());
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
