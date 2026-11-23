package org.student.guestblog.security.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.student.guestblog.domain.Authority;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.service.AccountService;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps a GitHub user profile to an application account.
 */
@Named("github")
@Singleton
public class GithubOauthAuthenticationMapper implements OauthAuthenticationMapper {

    private static final String USER_URL = "https://api.github.com/user";
    private static final String EMAILS_URL = "https://api.github.com/user/emails";

    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public GithubOauthAuthenticationMapper(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, State state) {
        return Mono.fromCallable(() -> {
            var profile = fetchProfile(tokenResponse.getAccessToken());
            var account = accountService.findOrCreateOauthAccount(
                "github", profile.email(), profile.name(), profile.avatar(), profile.id()
            );

            List<String> roles = account.getAuthorities();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("id", account.getId().toString());
            attributes.put("email", account.getEmail());
            if (account.getName() != null) {
                attributes.put("name", account.getName());
            }
            if (account.getAvatar() != null) {
                attributes.put("avatar", account.getAvatar());
            }
            return AuthenticationResponse.success(account.getEmail(), roles, attributes);
        });
    }

    private GithubProfile fetchProfile(String accessToken) throws IOException, InterruptedException {
        JsonNode user = get(USER_URL, accessToken);
        String email = text(user, "email");
        if (email == null) {
            email = fetchPrimaryEmail(accessToken);
        }
        String name = text(user, "name");
        if (name == null) {
            name = text(user, "login");
        }
        return new GithubProfile(
            user.path("id").asText(),
            email,
            name,
            text(user, "avatar_url")
        );
    }

    private String fetchPrimaryEmail(String accessToken) throws IOException, InterruptedException {
        JsonNode emails = get(EMAILS_URL, accessToken);
        for (JsonNode email : emails) {
            if (email.path("primary").asBoolean(false) && email.path("verified").asBoolean(false)) {
                return text(email, "email");
            }
        }
        for (JsonNode email : emails) {
            if (email.path("verified").asBoolean(false)) {
                return text(email, "email");
            }
        }
        return null;
    }

    private JsonNode get(String url, String accessToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
            .header("Authorization", "Bearer " + accessToken)
            .header("Accept", "application/vnd.github+json")
            .header("User-Agent", "guestblog")
            .GET()
            .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new ApplicationException(ErrorCode.GENERIC_ERROR_CODE);
        }
        return objectMapper.readTree(response.body());
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.path(field);
        return value.isNull() || value.isMissingNode() ? null : value.asText();
    }

    private record GithubProfile(String id, String email, String name, String avatar) {
    }
}
