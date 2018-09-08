package org.student.guestblog.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.student.guestblog.security.ApplicationAuthenticationProvider;
import org.student.guestblog.security.ApplicationSecurityContextRepository;
import org.student.guestblog.service.UserService;

/** Spring Security configuration. */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Setter(onMethod = @__(@Autowired))
  private ApplicationAuthenticationProvider authenticationProvider;

  @Setter(onMethod = @__(@Autowired))
  private ApplicationSecurityContextRepository securityContextRepository;

  @Setter(onMethod = @__(@Autowired))
  private UserService userService;

  /**
   * Returns password's encoder.
   *
   * @return {@link BCryptPasswordEncoder} bean.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  /**
   * Returns configured {@link WebSecurityConfigurerAdapter}.
   *
   * @return {@link WebSecurityConfigurerAdapter} bean.
   */
  @Bean
  public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
    return new WebSecurityConfigurerAdapter() {
      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http
          .httpBasic().disable()
          .formLogin().disable()
          .logout().disable()
          .csrf().disable()

          .userDetailsService(userService)
          .authenticationProvider(authenticationProvider)

          .securityContext()
          .securityContextRepository(securityContextRepository)

          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

          .and()
          .authorizeRequests()
          .antMatchers(HttpMethod.GET).permitAll()
          .antMatchers(
            "/api/users/register",
            "/api/users/sign/in",
            "/api/users/current"
          ).permitAll();
      }
    };
  }
}
