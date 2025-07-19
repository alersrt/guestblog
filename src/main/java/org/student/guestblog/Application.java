package org.student.guestblog;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * The main class of the application.
 */
public class Application {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * The endpoint of the application.
     *
     * @param args arguments of this app.
     */
    public static void main(String... args) {
        log.info("*** Starting application...");
        Micronaut.run(Application.class, args);
        log.info("*** Application started...");
    }
}
