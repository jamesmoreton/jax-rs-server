package com.jamesmoreton.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.CountryCode;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserCreateRequest {

  @NotNull
  private final UserType userType;
  @NotNull
  private final LocalDate dateOfBirth;
  @NotNull
  private final CountryCode countryCode;

  public UserCreateRequest(
      @JsonProperty("userType") UserType userType,
      @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
      @JsonProperty("countryCode") CountryCode countryCode
  ) {
    this.userType = userType;
    this.dateOfBirth = dateOfBirth;
    this.countryCode = countryCode;
  }

  public UserType getUserType() {
    return userType;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public CountryCode getCountryCode() {
    return countryCode;
  }
}
