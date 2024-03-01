package org.student.guestblog.security.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(OAuth2User oauth2User) {
        this.attributes = oauth2User.getAttributes();
    }

    public abstract String id();

    public abstract String name();

    public abstract String email();

    public abstract String imageUrl();
}
