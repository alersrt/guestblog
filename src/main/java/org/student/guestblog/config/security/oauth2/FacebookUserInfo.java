package org.student.guestblog.config.security.oauth2;

import java.util.Map;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class FacebookUserInfo extends OAuth2UserInfo {

  public FacebookUserInfo(OAuth2User oauth2User) {
    super(oauth2User);
  }

  @Override
  public String id() {
    return (String) attributes.get("id");
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
    if (attributes.containsKey("picture")) {
      Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
      if (pictureObj.containsKey("data")) {
        Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
        if (dataObj.containsKey("url")) {
          return (String) dataObj.get("url");
        }
      }
    }
    return null;
  }
}
