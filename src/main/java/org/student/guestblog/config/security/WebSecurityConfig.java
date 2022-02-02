package org.student.guestblog.config.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.student.guestblog.config.security.oauth2.CustomOAuth2UserService;
import org.student.guestblog.repository.AccountRepository;
import org.student.guestblog.repository.PassportRepository;
import org.student.guestblog.util.Cookie;

@Configuration
@EnableGlobalMethodSecurity(
    proxyTargetClass = true,
    prePostEnabled = true,
    jsr250Enabled = true
)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

  private static final Directive[] SOURCE = {
      Directive.CACHE,
      Directive.COOKIES,
      Directive.STORAGE,
      Directive.EXECUTION_CONTEXTS
  };

  private final AccountRepository accountRepository;
  private final PassportRepository passportRepository;
  private final PasswordEncoder passwordEncoder;
  private final DataSource dataSource;

  public WebSecurityConfig(
      AccountRepository accountRepository,
      PassportRepository passportRepository,
      PasswordEncoder passwordEncoder,
      DataSource dataSource
  ) {
    this.accountRepository = accountRepository;
    this.passportRepository = passportRepository;
    this.passwordEncoder = passwordEncoder;
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

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(customUserDetailsServiceBean())
        .passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
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
        .authorizeRequests()
        .anyRequest().permitAll()
        .and();

    // OAuth2
    http
        .oauth2Login()
        .defaultSuccessUrl("/")
        .userInfoEndpoint()
        .userService(oAuth2UserServiceBean())
        .and();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
