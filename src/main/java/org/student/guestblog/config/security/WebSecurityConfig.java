package org.student.guestblog.config.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.student.guestblog.config.security.oauth2.CustomOAuth2UserService;
import org.student.guestblog.repository.AccountRepository;
import org.student.guestblog.util.Cookie;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity(
  proxyTargetClass = true,
  prePostEnabled = true,
  jsr250Enabled = true
)
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

  private static final Directive[] SOURCE = {
    Directive.CACHE,
    Directive.COOKIES,
    Directive.STORAGE,
    Directive.EXECUTION_CONTEXTS
  };

  private final AccountRepository accountRepository;
  private final DataSource dataSource;

  public WebSecurityConfig(AccountRepository accountRepository,
                           DataSource dataSource) {
    this.accountRepository = accountRepository;
    this.dataSource = dataSource;
  }

  @Bean
  public UserDetailsService customUserDetailsServiceBean() throws Exception {
    return username -> {
      var account = accountRepository
        .findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

      return new User(account);
    };
  }

  /**
   * Password's encoder.
   *
   * @return {@link BCryptPasswordEncoder} bean.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserServiceBean() {
    return new CustomOAuth2UserService(accountRepository);
  }

  @Bean
  public JdbcTokenRepositoryImpl jdbcTokenRepository(DataSource dataSource) {
    var repository = new JdbcTokenRepositoryImpl();
    repository.setDataSource(dataSource);
    return repository;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Disable CORS and disable CSRF
    http.cors().disable().csrf().disable();

    // Set session management to never created
    http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.NEVER)
      .sessionFixation()
      .migrateSession()
      .and();

    // Set request cache to null
    http
      .requestCache()
      .requestCache(new NullRequestCache())
      .and();

    // Setup login
    http
      .httpBasic()
      .disable()
      .formLogin()
      .loginProcessingUrl("/api/auth/login")
      .successHandler((request, response, authentication) -> {
      })
      .failureHandler((request, response, exception) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
      .and()
      .rememberMe()
      .alwaysRemember(true)
      .tokenValiditySeconds(24 * 60 * 60)
      .useSecureCookie(true)
      .rememberMeCookieName(Cookie.X_AUTH_REMEMBER_ME)
      .userDetailsService(customUserDetailsServiceBean())
      .tokenRepository(jdbcTokenRepository(dataSource))
      .and();

    // Setup logout
    http
      .logout()
      .permitAll()
      .logoutUrl("/api/auth/logout")
      .clearAuthentication(true)
      .invalidateHttpSession(true)
      .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
      .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
      .and();

    // Setup permissions on endpoints
    http
      .authorizeHttpRequests()
      .anyRequest().permitAll()
      .and();

    // OAuth2
    http
      .oauth2Login()
      .userInfoEndpoint()
      .userService(oAuth2UserServiceBean());

    return http.build();
  }
}
