package com.jamesmoreton.user.model;

import com.neovisionaries.i18n.CountryCode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class User {

  private final UUID userUid;
  private final UserType userType;
  private final LocalDate dateOfBirth;
  private final CountryCode countryCode;
  private final ZonedDateTime createdAt;

  public User(
      UUID userUid,
      UserType userType,
      LocalDate dateOfBirth,
      CountryCode countryCode,
      ZonedDateTime createdAt
  ) {
    this.userUid = userUid;
    this.userType = userType;
    this.dateOfBirth = dateOfBirth;
    this.countryCode = countryCode;
    this.createdAt = createdAt;
  }

  public UUID getUserUid() {
    return userUid;
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

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }
}
