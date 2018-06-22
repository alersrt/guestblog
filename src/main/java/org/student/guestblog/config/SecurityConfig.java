package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/** Spring Security configuration. */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  /**
   * Register and configures {@link SecurityWebFilterChain}. There is configured access for http
   * requests.
   *
   * @return {@link SecurityWebFilterChain} bean.
   *
   * @see SecurityWebFilterChain
   */
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http.csrf().disable().authorizeExchange().anyExchange().permitAll().and().httpBasic();
    return http.build();
  }
}
