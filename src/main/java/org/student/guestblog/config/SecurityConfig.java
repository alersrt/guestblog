package org.student.guestblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.student.guestblog.security.ApplicationAuthenticationManager;
import org.student.guestblog.security.ApplicationSecurityContextRepository;
import org.student.guestblog.service.UserService;

/** Spring Security configuration. */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ApplicationAuthenticationManager authenticationManager;

  @Autowired
  private ApplicationSecurityContextRepository securityContextRepository;

  @Autowired
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

  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return authenticationManager;
  }

  @Override
  protected UserDetailsService userDetailsService() {
    return userService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .httpBasic().disable()
      .formLogin().disable()
      .logout().disable()
      .csrf().disable()

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


}
