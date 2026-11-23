package org.student.guestblog.storage.repository;

import jakarta.inject.Singleton;
import org.jooq.DSLContext;
import org.student.guestblog.domain.PassportType;
import org.student.guestblog.storage.entity.AccountEntity;
import org.student.guestblog.storage.entity.PassportEntity;
import org.student.guestblog.storage.jooq.tables.Account;
import org.student.guestblog.storage.jooq.tables.Passport;
import org.student.guestblog.storage.jooq.tables.records.AccountRecord;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class AccountRepository {

    private final DSLContext dsl;

    public AccountRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<AccountEntity> findById(UUID id) {
        return dsl.selectFrom(Account.ACCOUNT)
            .where(Account.ACCOUNT.GID.eq(id))
            .fetchOptional()
            .map(this::map);
    }

    public Optional<AccountEntity> findByEmail(String email) {
        return dsl.selectFrom(Account.ACCOUNT)
            .where(Account.ACCOUNT.EMAIL.eq(email))
            .fetchOptional()
            .map(this::map);
    }

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(
            dsl.selectOne()
                .from(Account.ACCOUNT)
                .where(Account.ACCOUNT.EMAIL.eq(email))
        );
    }

    public AccountEntity save(AccountEntity entity) {
        var now = OffsetDateTime.now();
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(now);
        }
        entity.setUpdateDate(now);

        var authorities = entity.getAuthorities() == null
            ? null
            : entity.getAuthorities().toArray(String[]::new);

        dsl.insertInto(Account.ACCOUNT)
            .set(Account.ACCOUNT.GID, entity.getId())
            .set(Account.ACCOUNT.CREATE_DATE, entity.getCreateDate())
            .set(Account.ACCOUNT.UPDATE_DATE, entity.getUpdateDate())
            .set(Account.ACCOUNT.EMAIL, entity.getEmail())
            .set(Account.ACCOUNT.NAME, entity.getName())
            .set(Account.ACCOUNT.AVATAR, entity.getAvatar())
            .set(Account.ACCOUNT.AUTHORITIES, authorities)
            .onConflict(Account.ACCOUNT.GID)
            .doUpdate()
            .set(Account.ACCOUNT.UPDATE_DATE, entity.getUpdateDate())
            .set(Account.ACCOUNT.EMAIL, entity.getEmail())
            .set(Account.ACCOUNT.NAME, entity.getName())
            .set(Account.ACCOUNT.AVATAR, entity.getAvatar())
            .set(Account.ACCOUNT.AUTHORITIES, authorities)
            .execute();

        savePassports(entity, now);

        return findById(entity.getId())
            .orElseThrow(() -> new IllegalStateException("Stored account is not found"));
    }

    private void savePassports(AccountEntity entity, OffsetDateTime now) {
        for (PassportEntity passport : entity.getPassports()) {
            if (passport.getId() == null) {
                passport.setId(UUID.randomUUID());
            }
            if (passport.getCreateDate() == null) {
                passport.setCreateDate(now);
            }
            passport.setUpdateDate(now);
            passport.setAccount(entity);

            dsl.insertInto(Passport.PASSPORT)
                .set(Passport.PASSPORT.GID, passport.getId())
                .set(Passport.PASSPORT.CREATE_DATE, passport.getCreateDate())
                .set(Passport.PASSPORT.UPDATE_DATE, passport.getUpdateDate())
                .set(Passport.PASSPORT.ACCOUNT_GID, entity.getId())
                .set(Passport.PASSPORT.TYPE, passport.getType().name())
                .set(Passport.PASSPORT.HASH, passport.getHash())
                .onConflict(Passport.PASSPORT.GID)
                .doUpdate()
                .set(Passport.PASSPORT.UPDATE_DATE, passport.getUpdateDate())
                .set(Passport.PASSPORT.TYPE, passport.getType().name())
                .set(Passport.PASSPORT.HASH, passport.getHash())
                .execute();
        }
    }

    private AccountEntity map(AccountRecord record) {
        var account = AccountEntity.builder()
            .id(record.getGid())
            .createDate(record.getCreateDate())
            .updateDate(record.getUpdateDate())
            .email(record.getEmail())
            .name(record.getName())
            .avatar(record.getAvatar())
            .authorities(record.getAuthorities() == null
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(record.getAuthorities())))
            .build();

        List<PassportEntity> passports = dsl.selectFrom(Passport.PASSPORT)
            .where(Passport.PASSPORT.ACCOUNT_GID.eq(account.getId()))
            .fetch()
            .map(passport -> PassportEntity.builder()
                .id(passport.getGid())
                .createDate(passport.getCreateDate())
                .updateDate(passport.getUpdateDate())
                .account(account)
                .type(PassportType.valueOf(passport.getType()))
                .hash(passport.getHash())
                .build());
        account.setPassports(passports);

        return account;
    }
}
