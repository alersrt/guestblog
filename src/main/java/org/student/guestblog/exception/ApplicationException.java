package org.student.guestblog.exception;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Describes exceptions of the current application.
 */
@RequiredArgsConstructor
public class ApplicationException extends RuntimeException {

  /** Code of this exception. */
  @NonNull
  private Code code = Code.GENERIC_ERROR_CODE;

  /**
   * Exception's constructor.
   *
   * @param message message which can to contain detailed information about this exception.
   * @param code exception code.
   */
  public ApplicationException(String message, Code code) {
    super(message);
    this.code = code;
  }

  /**
   * Returns information about exception code.
   *
   * @return integer value of the exception's code.
   */
  public int getCodeValue() {
    return code.getValue();
  }

  /** Describes exceptions' codes. */
  @RequiredArgsConstructor
  public static enum Code {
    GENERIC_ERROR_CODE(1);

    private int value;

    private Code(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }
}
