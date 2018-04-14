package org.student.guestblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** The main class of the application. */
@Slf4j
@SpringBootApplication
public class Application {

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
