package org.student.guestblog.service.impl;

import com.uber.cadence.activity.Activity;
import lombok.RequiredArgsConstructor;
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
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.rest.dto.user.UserUpdateRequest;
import org.student.guestblog.service.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Describes user's managing service and implements {@link UserDetailsService}.
 */
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<AccountEntity> getById(UUID accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Optional<UserResponse> getByEmail(String email) {
        return accountRepository.findByEmail(email).map(UserResponse::new);
    }

    @Override
    public Optional<UserResponse> create(RegisterRequest request) {
        AccountEntity accountEntity = AccountEntity.builder()
                .id(UUID.randomUUID())
                .email(request.email())
                .authorities(List.of(Authority.USER.getAuthority()))
                .build();

        PassportEntity passportEntity = PassportEntity.builder()
                .id(UUID.randomUUID())
                .account(accountEntity)
                .type(PassportType.PASSWORD)
                .hash(passwordEncoder.encode(request.password()))
                .build();
        accountEntity.getPassports().add(passportEntity);
        var isExist = accountRepository.existsByEmail(accountEntity.getEmail()) || "admin@test.dev".equals(accountEntity.getEmail());
        // if (isExist) {            
        //     throw Activity.wrap(new Exception("Email is not available"));
        // }
        return isExist ? Optional.empty() : Optional.of(accountRepository.save(accountEntity)).map(UserResponse::new);
    }

    @Override
    public AccountEntity update(UUID id, UserUpdateRequest request) {
        var account = accountRepository.findById(id).orElseThrow(() -> new ApplicationException(ErrorCode.GENERIC_ERROR_CODE));
        request.username().ifPresent(account::setEmail);
        request.password().map(passwordEncoder::encode).ifPresent(s -> account
                .getPassports().stream()
                .filter(passport -> passport.getType().equals(PassportType.PASSWORD))
                .findFirst()
                .orElseThrow()
                .setHash(s)
        );
        return accountRepository.save(account);
    }
}
