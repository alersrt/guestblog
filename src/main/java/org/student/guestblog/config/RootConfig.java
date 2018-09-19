package org.student.guestblog.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
