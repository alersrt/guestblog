package org.student.guestblog.config.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.student.guestblog.config.security.User;
import org.student.guestblog.data.entity.AccountEntity;
import org.student.guestblog.data.entity.PassportEntity;
import org.student.guestblog.data.repository.AccountRepository;
import org.student.guestblog.exception.ApplicationException;
import org.student.guestblog.exception.ErrorCode;
import org.student.guestblog.model.PassportType;

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
            default -> throw new ApplicationException(ErrorCode.GENERIC_ERROR_CODE);
        };

        var existed = accountRepository.findByEmail(info.email()).orElse(AccountEntity.builder()
            .email(info.email())
            .avatar(info.imageUrl())
            .build());

        var identity = existed.getPassports().stream()
            .filter(passport -> registrationId.equals(passport.getType().id()))
            .findFirst();

        if (identity.isEmpty()) {
            existed.getPassports().add(new PassportEntity(existed, PassportType.get(registrationId), info.id()));
        }

        accountRepository.saveAndFlush(existed);

        return new User(existed, registrationId, user.getAttributes());
    }
}
