package com.jamesmoreton.exception;

public class RequestValidationException extends RuntimeException {

  public enum ErrorCode {
    USER_NOT_FOUND
  }

  private final ErrorCode errorCode;

  private RequestValidationException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public static RequestValidationException forCode(ErrorCode errorCode, String message) {
    return new RequestValidationException(errorCode, message);
  }
}
