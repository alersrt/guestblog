package org.student.guestblog.model;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;

public enum PassportType {
  PASSWORD("password"),
  FACEBOOK("facebook"),
  GOOGLE("google"),
  GITHUB("github");

  private final String id;

  PassportType(String id) {
    this.id = id;
  }

  public static PassportType get(@NotNull String id) {
    return Arrays.stream(PassportType.values())
      .filter(passportType -> id.equals(passportType.id()))
      .findFirst()
      .orElseThrow();
  }

  public String id() {
    return id;
  }
}
