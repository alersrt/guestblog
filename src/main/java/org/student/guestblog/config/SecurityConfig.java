package org.student.guestblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.student.guestblog.security.AuthenticationManager;
import org.student.guestblog.security.SecurityContextRepository;
import org.student.guestblog.service.UserService;

/** Spring Security configuration. */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SecurityContextRepository securityContextRepository;

  @Autowired
  private UserService userService;

  /**
   * Register and configures {@link SecurityWebFilterChain}. There is configured access for http requests.
   *
   * @return {@link SecurityWebFilterChain} bean.
   * @see SecurityWebFilterChain
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .httpBasic().disable()
      .formLogin().disable()
      .logout().disable()
      .csrf().disable()

      .authenticationManager(this.authenticationManager)
      .securityContextRepository(this.securityContextRepository)

      .authorizeExchange()
      .pathMatchers(HttpMethod.GET).permitAll()
      .pathMatchers("/api/users/register").permitAll()
      .pathMatchers("/api/users/login").permitAll()
      .pathMatchers("/api/users/current").permitAll()
      .anyExchange().permitAll()
      .and()
      .build();
  }

  /**
   * Returns default user details service.
   *
   * @return {@link ReactiveUserDetailsService} bean.
   */
  @Bean
  @Primary
  public ReactiveUserDetailsService userDetailsService() {
    return userService;
  }

  /**
   * Returns password's encoder.
   *
   * @return {@link BCryptPasswordEncoder} bean.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }
}
