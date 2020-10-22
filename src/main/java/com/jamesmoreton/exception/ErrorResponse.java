package com.jamesmoreton.exception;

public class ErrorResponse {

  private final String error;
  private final String errorDescription;

  public ErrorResponse(
      String error,
      String errorDescription
  ) {
    this.error = error;
    this.errorDescription = errorDescription;
  }

  public String getError() {
    return error;
  }

  public String getErrorDescription() {
    return errorDescription;
  }
}
