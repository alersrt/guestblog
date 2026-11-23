package org.student.guestblog.security;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Encodes and verifies passwords using the BCrypt algorithm.
 */
@Singleton
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword != null && BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
