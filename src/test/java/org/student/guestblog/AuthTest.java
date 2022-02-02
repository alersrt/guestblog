package org.student.guestblog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.student.guestblog.model.Authority;
import org.student.guestblog.rest.dto.register.RegisterRequest;
import org.student.guestblog.rest.dto.user.UserResponse;
import org.student.guestblog.util.Cookie;

/**
 * Check working of the authentication configuration.
 */
@DisplayName("Authentication infrastructure test")
public class AuthTest extends AbstractIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @DisplayName("login: successful")
  @Test
  void obtainToken_positive() throws Exception {
    /*------ Arranges ------*/
    String username = "user@test.dev";
    String password = "password";
    String clientAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());

    /*------ Actions ------*/
    log.info("===> LOGIN");
    ResultActions loginAction = mockMvc.perform(
        post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Basic %s", clientAuth))
    );
    var authResponse = loginAction.andReturn().getResponse();
    UserResponse authDto = objectMapper.readValue(
        authResponse.getContentAsString(),
        UserResponse.class
    );

    var authCookie = Arrays.stream(authResponse.getCookies())
        .filter(cookie -> cookie.getName().equals(Cookie.X_AUTH_REMEMBER_ME))
        .findFirst()
        .get();

    log.info("===> CURRENT USER");
    ResultActions currentUserAction = mockMvc.perform(
        get("/api/account/me")
            .cookie(authCookie)
    );
    UserResponse meDto = objectMapper.readValue(
        currentUserAction
            .andReturn()
            .getResponse()
            .getContentAsString(),
        UserResponse.class
    );

    /*------ Asserts ------*/
    loginAction.andExpect(status().isOk());
    currentUserAction.andExpect(status().isOk());
    assertAll("check response",
        () -> assertThat(Arrays.stream(authResponse.getCookies())
            .filter(cookie -> cookie.getName().equals(Cookie.X_AUTH_REMEMBER_ME))
            .findFirst()).isNotEmpty(),
        () -> assertThat(meDto).isEqualTo(authDto)
    );
  }

  @DisplayName("login: unsuccessful")
  @Test
  void obtainToken_negative() throws Exception {
    /*------ Arranges ------*/
    String username = "user@test.dev";
    String password = "wrong_password";
    String clientAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());

    /*------ Actions ------*/
    ResultActions resultActions = mockMvc.perform(
        post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", String.format("Basic %s", clientAuth))
    );

    var authResponse = resultActions.andReturn().getResponse();

    /*------ Asserts ------*/
    resultActions.andExpect(status().isUnauthorized());
  }
}
