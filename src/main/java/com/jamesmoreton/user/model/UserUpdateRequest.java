package com.jamesmoreton.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class UserUpdateRequest {

  @NotNull
  private final UserType userType;

  public UserUpdateRequest(
      @JsonProperty("userType") UserType userType
  ) {
    this.userType = userType;
  }

  public UserType getUserType() {
    return userType;
  }
}
