package org.student.guestblog.security.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUserInfo extends OAuth2UserInfo {

    public GoogleUserInfo(OAuth2User oauth2User) {
        super(oauth2User);
    }

    @Override
    public String id() {
        return (String) attributes.get("sub");
    }

    @Override
    public String name() {
        return (String) attributes.get("name");
    }

    @Override
    public String email() {
        return (String) attributes.get("email");
    }

    @Override
    public String imageUrl() {
        return (String) attributes.get("picture");
    }
}
