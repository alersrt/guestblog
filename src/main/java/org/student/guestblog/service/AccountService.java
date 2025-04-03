package org.student.guestblog.service;

import org.student.guestblog.data.entity.AccountEntity;

import java.util.Optional;
import java.util.UUID;


public interface AccountService {

    Optional<AccountEntity> getById(UUID accountId);

    /**
     * Return user by its email.
     *
     * @param email user's email.
     * 
     * @return user.
     */
    Optional<AccountEntity> getByEmail(String email);

    Optional<AccountEntity> create(String email, String password);

    AccountEntity update(UUID id, Optional<String> email, Optional<String> password);
}
