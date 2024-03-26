package com.adurolife.exercise.exceptions;

public class BowlingGameException extends RuntimeException {

  public BowlingGameException() {
    super();
  }

  public BowlingGameException(String message) {
    super(message);
  }

  public BowlingGameException(String message, Throwable cause) {
    super(message, cause);
  }

  public BowlingGameException(Throwable cause) {
    super(cause);
  }

  protected BowlingGameException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
