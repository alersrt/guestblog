package org.student.guestblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/** Spring Security configuration. */
@Configuration
public class SecurityConfig {

  /**
   * Register and configures {@link WebSecurityConfigurerAdapter}. There is configured access for
   * http requests.
   *
   * @return {@link WebSecurityConfigurerAdapter} bean.
   * @see WebSecurityConfigurerAdapter
   */
  @Bean
  public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
    return new WebSecurityConfigurerAdapter() {
      @Override
      protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
            .and()
            .httpBasic();
      }
    };
  }
}
