package org.student.guestblog.config.security.oauth2;

import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

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
