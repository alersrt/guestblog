package org.student.guestblog.rest.dto.register;

import java.util.Optional;

public record RegisterRequest(String username, String password, Optional<String> email) {}
