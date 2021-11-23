package org.student.guestblog.service;

import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ApplicationException.Code;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;
import org.student.guestblog.repository.AccountRepository;

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
   * Return user by its username.
   *
   * @param username user's username.
   * @return user.
   */
  public Optional<Account> getByUsername(String username) {
    return accountRepository.findByUsername(username);
  }

  public Optional<Account> create(String username, String password, String email) {
    Account account = new Account()
        .setUsername(username.toLowerCase())
        .setEmail(email)
        .setPassword(passwordEncoder.encode(password))
        .setAuthorities(Set.of(Authority.USER));

    var isExist = accountRepository.existsByUsername(account.getUsername()) || "admin".equals(account.getUsername());
    return isExist ? Optional.empty() : Optional.of(accountRepository.save(account));
  }

  public Account update(Long id, Optional<String> username, Optional<String> password) {
    var account = accountRepository.findById(id).orElseThrow(() -> new ApplicationException(Code.GENERIC_ERROR_CODE));
    username.ifPresent(account::setUsername);
    password.map(passwordEncoder::encode).ifPresent(account::setPassword);
    return accountRepository.save(account);
  }
}
