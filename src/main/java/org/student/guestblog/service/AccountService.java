package org.student.guestblog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ApplicationException.Code;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;
import org.student.guestblog.model.Passport;
import org.student.guestblog.model.PassportType;
import org.student.guestblog.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Account> getById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Return user by its email.
     *
     * @param email user's email.
     * @return user.
     */
    public Optional<Account> getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> create(String email, String password) {
        Account account = new Account()
            .setEmail(email)
            .setAuthorities(List.of(Authority.USER));

        Passport passwordPass = new Passport(account, PassportType.PASSWORD, passwordEncoder.encode(password));
        account.passports().add(passwordPass);

        var isExist = accountRepository.existsByEmail(account.email()) || "admin@test.dev".equals(account.email());
        return isExist ? Optional.empty() : Optional.of(accountRepository.save(account));
    }

    public Account update(Long id, Optional<String> email, Optional<String> password) {
        var account = accountRepository.findById(id).orElseThrow(() -> new ApplicationException(Code.GENERIC_ERROR_CODE));
        email.ifPresent(account::setEmail);
        password.map(passwordEncoder::encode).ifPresent(s -> account
            .passports().stream()
            .filter(passport -> passport.type().equals(PassportType.PASSWORD))
            .findFirst()
            .orElseThrow()
            .setHash(s)
        );
        return accountRepository.save(account);
    }
}
