package com.jamesmoreton.user;

import com.jamesmoreton.exception.RequestValidationException;
import com.jamesmoreton.user.model.User;
import com.jamesmoreton.user.model.UserCreateRequest;
import com.jamesmoreton.user.model.UserType;
import com.jamesmoreton.user.model.UserUpdateRequest;
import com.jamesmoreton.user.model.Users;
import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.jamesmoreton.exception.RequestValidationException.ErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {

  private static final LocalDate DATE_OF_BIRTH = LocalDate.EPOCH;
  private static final ZonedDateTime NOW = ZonedDateTime.of(2020, 10, 21, 0, 0, 0, 0, ZoneId.of("Z"));

  private final Clock clock = mock(Clock.class);

  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService(clock);
    given(clock.instant()).willReturn(NOW.toInstant());
    given(clock.getZone()).willReturn(ZoneId.of("Z"));
  }

  @Test
  void createAndGetUser() {
    // Given
    UserCreateRequest request = new UserCreateRequest(UserType.BASIC, DATE_OF_BIRTH, CountryCode.GB);

    // When
    UUID userUid = underTest.createUser(request);

    // Then
    Optional<User> user = underTest.getUser(userUid);
    assertThat(user).hasValueSatisfying(u ->
        assertThat(u).usingRecursiveComparison().isEqualTo(
            new User(
                userUid,
                UserType.BASIC,
                DATE_OF_BIRTH,
                CountryCode.GB,
                ZonedDateTime.now(clock)
            )
        )
    );
  }

  @Test
  void updateUser_userDoesNotExist() {
    // Given
    UserUpdateRequest updateRequest = new UserUpdateRequest(UserType.PREMIUM);

    // When / Then
    assertThatThrownBy(() -> underTest.updateUser(UUID.randomUUID(), updateRequest))
        .isInstanceOf(RequestValidationException.class)
        .satisfies(e -> assertThat(((RequestValidationException) e).getErrorCode()).isEqualTo(USER_NOT_FOUND))
        .hasMessage("User to update not found");
  }

  @Test
  void updateUser() {
    // Given
    UserCreateRequest createRequest = new UserCreateRequest(UserType.BASIC, DATE_OF_BIRTH, CountryCode.GB);
    UUID userUid = underTest.createUser(createRequest);
    UserUpdateRequest updateRequest = new UserUpdateRequest(UserType.PREMIUM);

    // When
    underTest.updateUser(userUid, updateRequest);

    // Then
    Optional<User> user = underTest.getUser(userUid);
    assertThat(user).hasValueSatisfying(u ->
        assertThat(u).usingRecursiveComparison().isEqualTo(new User(
            userUid,
            UserType.PREMIUM,
            DATE_OF_BIRTH,
            CountryCode.GB,
            ZonedDateTime.now(clock)
        ))
    );
  }

  @Test
  void deleteUser() {
    // Given
    UserCreateRequest request = new UserCreateRequest(UserType.BASIC, DATE_OF_BIRTH, CountryCode.GB);
    UUID userUid = underTest.createUser(request);

    // When
    underTest.deleteUser(userUid);

    // Then
    Optional<User> user = underTest.getUser(userUid);
    assertThat(user).isEmpty();
  }

  @Test
  void getUsers_byUserType() {
    // Given
    seedUsers();

    // When
    Users users = underTest.getUsers(UserType.BASIC, null);

    // Then
    assertThat(users.getUsers()).hasSize(2);
  }

  @Test
  void getUsers_byCountryCode() {
    // Given
    seedUsers();

    // When
    Users users = underTest.getUsers(null, CountryCode.GB);

    // Then
    assertThat(users.getUsers()).hasSize(2);
  }

  @Test
  void getUsers_byUserTypeAndCountryCode() {
    // Given
    seedUsers();

    // When
    Users users = underTest.getUsers(UserType.PREMIUM, CountryCode.US);

    // Then
    assertThat(users.getUsers()).hasSize(1);
  }

  @Test
  void getUsers_all() {
    // Given
    seedUsers();

    // When
    Users users = underTest.getUsers(null, null);

    // Then
    assertThat(users.getUsers()).hasSize(4);
  }

  private void seedUsers() {
    UserCreateRequest createRequest1 = new UserCreateRequest(UserType.BASIC, DATE_OF_BIRTH, CountryCode.GB);
    UserCreateRequest createRequest2 = new UserCreateRequest(UserType.BASIC, DATE_OF_BIRTH, CountryCode.US);
    UserCreateRequest createRequest3 = new UserCreateRequest(UserType.PREMIUM, DATE_OF_BIRTH, CountryCode.GB);
    UserCreateRequest createRequest4 = new UserCreateRequest(UserType.PREMIUM, DATE_OF_BIRTH, CountryCode.US);
    underTest.createUser(createRequest1);
    underTest.createUser(createRequest2);
    underTest.createUser(createRequest3);
    underTest.createUser(createRequest4);
  }
}
