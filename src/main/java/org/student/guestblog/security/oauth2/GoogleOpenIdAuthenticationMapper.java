package org.student.guestblog.security.oauth2;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.student.guestblog.domain.Authority;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.service.AccountService;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps Google OpenID Connect claims to an application account.
 */
@Named("google")
@Singleton
public class GoogleOpenIdAuthenticationMapper implements OpenIdAuthenticationMapper {

    private final AccountService accountService;

    public GoogleOpenIdAuthenticationMapper(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(String providerName,
                                                                          OpenIdTokenResponse tokenResponse,
                                                                          OpenIdClaims openIdClaims,
                                                                          State state) {
        return Mono.fromCallable(() -> {
            String email = openIdClaims.getEmail();
            if (email == null) {
                throw new ApplicationException(ErrorCode.GENERIC_ERROR_CODE);
            }

            var account = accountService.findOrCreateOauthAccount(
                providerName,
                email,
                openIdClaims.getName(),
                openIdClaims.getPicture(),
                openIdClaims.getSubject()
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
}
