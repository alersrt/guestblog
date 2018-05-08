package org.student.guestblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;

/** The main class of the application. */
@Slf4j
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

  /** {@inheritDoc} */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  /**
   * The endpoint of the application.
   *
   * @param args arguments of this app.
   */
  public static void main(String... args) {
    log.info("*** Starting application...");
    SpringApplication.run(Application.class, args);
    log.info("*** Application started...");
  }
}
