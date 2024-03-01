package org.student.guestblog.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.model.Authority;
import org.student.guestblog.model.PassportType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record User(
    UUID id,
    String email,
    String name,
    String avatar,
    List<Authority> authorities,
    String hash,
    String clientName,
    Map<String, Object> attributes
) implements UserDetails, OAuth2User {

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
            null
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
                .orElseThrow()
                .getHash(),
            clientName,
            attributes
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return name;
    }
}
