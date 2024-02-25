package org.student.guestblog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.data.entity.PassportEntity;
import org.student.guestblog.data.repository.AccountRepository;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.model.Authority;
import org.student.guestblog.model.PassportType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Describes user's managing service and implements {@link UserDetailsService}.
 */
@Service
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
}
