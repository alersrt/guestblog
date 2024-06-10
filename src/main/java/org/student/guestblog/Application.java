package org.student.guestblog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.invoke.MethodHandles;

/**
 * The main class of the application.
 */
@SpringBootApplication(scanBasePackages = "org.student.*")
public class Application {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
