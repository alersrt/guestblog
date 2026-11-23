package org.student.guestblog.security;

import io.micronaut.security.authentication.Authentication;
import org.student.guestblog.domain.Authority;
import org.student.guestblog.domain.PassportType;
import org.student.guestblog.storage.entity.AccountEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The authenticated principal of the application.
 */
public record User(
    UUID id,
    String email,
    String name,
    String avatar,
    List<Authority> authorities,
    String hash,
    String clientName,
    Map<String, Object> attributes
) implements Authentication {

    public User(AccountEntity model) {
        this(
            model.getId(),
            model.getEmail(),
            model.getName(),
            model.getAvatar(),
            model.getAuthorities().stream().map(Authority::valueOf).toList(),
            model.getPassports().stream()
                .filter(passport -> PassportType.PASSWORD.equals(passport.getType()))
                .findFirst()
                .orElseThrow()
                .getHash(),
            null,
            Map.of()
        );
    }

    public User(AccountEntity model, String clientName, Map<String, Object> attributes) {
        this(
            model.getId(),
            model.getEmail(),
            model.getName(),
            model.getAvatar(),
            model.getAuthorities().stream().map(Authority::valueOf).toList(),
            model.getPassports().stream()
                .filter(passport -> clientName.equals(passport.getType().id()))
                .findFirst()
                .map(passport -> passport.getHash())
                .orElse(null),
            clientName,
            attributes == null ? Map.of() : attributes
        );
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes == null ? Map.of() : attributes;
    }

    @Override
    public Collection<String> getRoles() {
        return authorities.stream().map(Authority::getAuthority).toList();
    }
}
