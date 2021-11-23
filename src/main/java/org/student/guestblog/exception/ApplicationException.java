package org.student.guestblog.exception;

/**
 * Describes exceptions of the current application.
 */
public class ApplicationException extends RuntimeException {

  /**
   * Code of this exception.
   */
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

  public ApplicationException(Code code) {
    this(null, code);
  }

  /**
   * Returns information about exception code.
   *
   * @return integer value of the exception's code.
   */
  public int getCodeValue() {
    return code.getValue();
  }

  /**
   * Describes exceptions' codes.
   */
  public enum Code {
    GENERIC_ERROR_CODE(1);

    private final int value;

    Code(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }
}
