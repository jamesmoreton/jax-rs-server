package com.jamesmoreton.user;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jamesmoreton.exception.RequestValidationException;
import com.jamesmoreton.user.model.User;
import com.jamesmoreton.user.model.UserCreateRequest;
import com.jamesmoreton.user.model.UserType;
import com.jamesmoreton.user.model.UserUpdateRequest;
import com.jamesmoreton.user.model.Users;
import com.neovisionaries.i18n.CountryCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.jamesmoreton.exception.RequestValidationException.ErrorCode.USER_NOT_FOUND;

@Singleton
public class UserService implements UserResource {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  // Mock database
  private final Map<UUID, User> userMap = new HashMap<>();

  private final Clock clock;

  @Inject
  public UserService(Clock clock) {
    this.clock = clock;
  }

  @Override
  public UUID createUser(UserCreateRequest request) {
    final UUID userUid = UUID.randomUUID();
    logger.info("Creating user {}", userUid);

    final User user = new User(
        userUid,
        request.getUserType(),
        request.getDateOfBirth(),
        request.getCountryCode(),
        ZonedDateTime.now(clock)
    );
    userMap.put(userUid, user);
    return userUid;
  }

  @Override
  public void updateUser(UUID userUid, UserUpdateRequest request) {
    final User user = Optional.ofNullable(userMap.get(userUid))
        .orElseThrow(() -> RequestValidationException.forCode(USER_NOT_FOUND, "User to update not found"));
    logger.info("Updating user {} to {}", userUid, request.getUserType());

    final User updated = new User(
        userUid,
        request.getUserType(),
        user.getDateOfBirth(),
        user.getCountryCode(),
        user.getCreatedAt()
    );
    userMap.put(userUid, updated);
  }

  @Override
  public void deleteUser(UUID userUid) {
    logger.info("Deleting user {}", userUid);
    userMap.remove(userUid);
  }

  @Override
  public Optional<User> getUser(UUID userUid) {
    logger.info("Getting user {}", userUid);
    return Optional.ofNullable(userMap.get(userUid));
  }

  @Override
  public Users getUsers(UserType userType, CountryCode countryCode) {
    logger.info("Getting users with userType={}, countryCode={}", userType, countryCode);
    final List<User> users = userMap.values()
        .stream()
        .filter(u -> userType == null || u.getUserType().equals(userType))
        .filter(u -> countryCode == null || u.getCountryCode().equals(countryCode))
        .collect(Collectors.toList());
    return new Users(users);
  }
}
