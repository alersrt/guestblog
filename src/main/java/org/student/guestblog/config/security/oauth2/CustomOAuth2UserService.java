package org.student.guestblog.config.security.oauth2;

import java.util.Set;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.student.guestblog.config.security.User;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ApplicationException.Code;
import org.student.guestblog.model.Account;
import org.student.guestblog.model.Authority;
import org.student.guestblog.model.Passport;
import org.student.guestblog.model.PassportType;
import org.student.guestblog.repository.AccountRepository;
import org.student.guestblog.repository.PassportRepository;

public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final AccountRepository accountRepository;

  public CustomOAuth2UserService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    OAuth2User user = super.loadUser(userRequest);

    var info = switch (registrationId) {
      case "facebook" -> new FacebookUserInfo(user);
      case "google" -> new GoogleUserInfo(user);
      default -> throw new ApplicationException(Code.GENERIC_ERROR_CODE);
    };

    var existed = accountRepository.findByEmail(info.email()).orElse(new Account(info.email()).setAvatar(info.imageUrl()));

    var identity = existed.passports().stream()
        .filter(passport -> registrationId.equals(passport.type().id()))
        .findFirst();

    if (identity.isEmpty()) {
      existed.passports().add(new Passport(existed, PassportType.get(registrationId), info.id()));
    }

    accountRepository.saveAndFlush(existed);

    return new User(existed, registrationId, user.getAttributes());
  }
}
