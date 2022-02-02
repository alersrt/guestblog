package org.student.guestblog.model;

import java.util.Arrays;
import javax.validation.constraints.NotNull;

public enum PassportType {
  PASSWORD("password"),
  FACEBOOK("facebook"),
  GOOGLE("google"),
  GITHUB("github");

  private final String id;

  PassportType(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }

  public static PassportType get(@NotNull String id) {
    return Arrays.stream(PassportType.values())
        .filter(passportType -> id.equals(passportType.id()))
        .findFirst()
        .orElseThrow();
  };
}
