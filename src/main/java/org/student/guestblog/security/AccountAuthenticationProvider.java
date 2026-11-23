package org.student.guestblog.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;
import org.student.guestblog.domain.PassportType;
import org.student.guestblog.storage.repository.AccountRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Authenticates accounts by their e-mail and password.
 */
@Singleton
public class AccountAuthenticationProvider implements AuthenticationProvider<HttpRequest<?>, String, String> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountAuthenticationProvider(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse authenticate(HttpRequest<?> request,
                                               AuthenticationRequest<String, String> authenticationRequest) {
        var account = accountRepository.findByEmail(authenticationRequest.getIdentity());
        if (account.isEmpty()) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
        }

        var passwordPassport = account.get().getPassports().stream()
            .filter(passport -> PassportType.PASSWORD.equals(passport.getType()))
            .findFirst();
        if (passwordPassport.isEmpty()) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(authenticationRequest.getSecret(), passwordPassport.get().getHash())) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
        }

        var user = new User(account.get());
        return AuthenticationResponse.success(user.getName(), user.getRoles(), attributes(user));
    }

    private Map<String, Object> attributes(User user) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", user.id().toString());
        attributes.put("email", user.email());
        if (user.name() != null) {
            attributes.put("name", user.name());
        }
        if (user.avatar() != null) {
            attributes.put("avatar", user.avatar());
        }
        return attributes;
    }
}
