package org.student.guestblog;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** The main class of the application. */
@Log
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
