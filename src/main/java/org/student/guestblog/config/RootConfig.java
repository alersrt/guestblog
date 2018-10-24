package org.student.guestblog.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** The root configuration of the application. */
@Configuration
public class RootConfig {

  /**
   * Describes Tika's bean.
   *
   * @return bean of {@link Tika} class.
   */
  @Bean
  public Tika tika() {
    return new Tika();
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
