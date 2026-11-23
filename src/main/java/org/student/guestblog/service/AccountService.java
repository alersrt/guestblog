package org.student.guestblog.service;

import jakarta.inject.Singleton;
import org.student.guestblog.domain.Authority;
import org.student.guestblog.domain.PassportType;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.security.PasswordEncoder;
import org.student.guestblog.storage.entity.AccountEntity;
import org.student.guestblog.storage.entity.PassportEntity;
import org.student.guestblog.storage.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Describes user's managing service.
 */
@Singleton
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(
        AccountRepository accountRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AccountEntity> getById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Return user by its email.
     *
     * @param email user's email.
     * @return user.
     */
    public Optional<AccountEntity> getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<AccountEntity> create(String email, String password) {
        AccountEntity accountEntity = AccountEntity.builder()
            .id(UUID.randomUUID())
            .email(email)
            .authorities(List.of(Authority.USER.getAuthority()))
            .build();

        PassportEntity passwordPass = new PassportEntity(accountEntity, PassportType.PASSWORD, passwordEncoder.encode(password));
        accountEntity.getPassports().add(passwordPass);

        var isExist = accountRepository.existsByEmail(accountEntity.getEmail()) || "admin@test.dev".equals(accountEntity.getEmail());
        return isExist ? Optional.empty() : Optional.of(accountRepository.save(accountEntity));
    }

    public AccountEntity update(UUID id, Optional<String> email, Optional<String> password) {
        var account = accountRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.GENERIC_ERROR_CODE));
        email.ifPresent(account::setEmail);
        password.map(passwordEncoder::encode).ifPresent(s -> account
            .getPassports().stream()
            .filter(passport -> passport.getType().equals(PassportType.PASSWORD))
            .findFirst()
            .orElseThrow()
            .setHash(s)
        );
        return accountRepository.save(account);
    }

    /**
     * Finds an account by the e-mail or creates a new one, and binds the external identity to it.
     *
     * @param provider   registration id of the OAuth2 provider.
     * @param email      e-mail of the external account.
     * @param name       display name of the external account.
     * @param avatar     avatar URL of the external account.
     * @param externalId identifier of the external account.
     * @return the stored account.
     */
    public AccountEntity findOrCreateOauthAccount(String provider, String email, String name, String avatar, String externalId) {
        var account = accountRepository.findByEmail(email).orElseGet(() -> AccountEntity.builder()
            .id(UUID.randomUUID())
            .email(email)
            .authorities(List.of(Authority.USER.getAuthority()))
            .build());

        if (name != null) {
            account.setName(name);
        }
        if (avatar != null) {
            account.setAvatar(avatar);
        }

        boolean identityExists = account.getPassports().stream()
            .anyMatch(passport -> provider.equals(passport.getType().id()));
        if (!identityExists) {
            account.getPassports().add(new PassportEntity(account, PassportType.get(provider), externalId));
        }

        return accountRepository.save(account);
    }
}
