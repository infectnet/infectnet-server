package io.infectnet.server.service.user;

import io.infectnet.server.persistence.token.Token;
import io.infectnet.server.persistence.token.TokenStorage;
import io.infectnet.server.persistence.user.User;
import io.infectnet.server.persistence.user.UserStorage;
import io.infectnet.server.service.converter.ConverterService;
import io.infectnet.server.service.encrypt.EncrypterService;
import io.infectnet.server.service.user.exception.InvalidEmailException;
import io.infectnet.server.service.user.exception.InvalidPasswordException;
import io.infectnet.server.service.user.exception.InvalidTokenException;
import io.infectnet.server.service.user.exception.InvalidUserNameException;
import io.infectnet.server.service.user.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

  private static final int PASSWORD_MIN_LENGTH = 8;

  private static final Pattern hasAtLeastOneNumberPattern = Pattern.compile(".*\\d+.*");

  private static final Pattern hasAtLeastOneUppercaseLetterPattern = Pattern.compile(".*[A-Z]+.*");

  private static final Pattern emailAddressPattern = Pattern.compile(".+@.+");

  private final UserStorage userStorage;

  private final TokenStorage tokenStorage;

  private final ConverterService converterService;

  private final EncrypterService encrypterService;

  public UserServiceImpl(UserStorage userStorage, TokenStorage tokenStorage,
                         ConverterService converterService, EncrypterService encrypterService) {
    this.userStorage = userStorage;
    this.tokenStorage = tokenStorage;
    this.converterService = converterService;
    this.encrypterService = encrypterService;
  }

  @Override
  public List<UserDTO> getAllUsers() {
    List<User> userList = userStorage.getAllUsers();

    List<UserDTO> userDTOList = converterService.map(userList, UserDTO.class);

    return Collections.unmodifiableList(userDTOList);
  }

  @Override
  public UserDTO register(String token, String email, String username, String password) throws
      ValidationException {

    isRegisterValid(Objects.requireNonNull(token),
        Objects.requireNonNull(email),
        Objects.requireNonNull(username),
        Objects.requireNonNull(password));

    String hashedPassword = encrypterService.hash(password);

    UserDTO newUser = new UserDTO(username, email, hashedPassword, LocalDateTime.now());

    User user = converterService.map(newUser, User.class);
    userStorage.saveUser(user);

    Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);
    tokenStorage.deleteToken(foundToken.get());

    return newUser;
  }

  @Override
  public Optional<UserDTO> login(String username, String password) {
    Optional<User> user = userStorage.getUserByUserName(Objects.requireNonNull(username));

    if (user.isPresent()
        && encrypterService.check(password, user.get().getPassword())) {

      UserDTO userDTO = converterService.map(user.get(), UserDTO.class);

      return Optional.of(userDTO);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public boolean exists(UserDTO user) {
    return userStorage.exists(converterService.map(Objects.requireNonNull(user), User.class));
  }

  /**
   * Checks if the data given at registration is valid.
   * @param token the token from the current registration
   * @param email the email address from the current registration
   * @param username the username from the current registration
   * @param password the password from the current registration
   * @throws ValidationException if any of the specified fields is invalid
   */
  private void isRegisterValid(String token, String email, String username, String password)
      throws ValidationException {
    ValidationException firstException = null;

    try {
      tokenIsValid(token);
    } catch (InvalidTokenException e) {
      e.setNextException(firstException);

      firstException = e;
    }

    try {
      passwordIsValid(password);
    } catch (InvalidPasswordException e) {
      e.setNextException(firstException);

      firstException = e;
    }

    try {
      emailsIsValid(email);

      emailIsUnique(email);
    } catch (InvalidEmailException e) {
      e.setNextException(firstException);

      firstException = e;
    }

    try {
      usernameIsValid(username);

      usernameIsUnique(username);
    } catch (InvalidUserNameException e) {
      e.setNextException(firstException);

      firstException = e;
    }
    if (firstException != null) {
      throw firstException;
    }
  }

  /**
   * Checks if the token given is in the storage.
   * @param token the token to check
   * @throws InvalidTokenException if the token is not found in the storage
   */
  private void tokenIsValid(String token) throws InvalidTokenException {
    Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);

    if (!foundToken.isPresent()) {
      throw new InvalidTokenException();
    }
  }

  /**
   * Checks whether the given email is valid.
   * @param email the email address to check
   * @throws InvalidEmailException if the specified string is not a valid email address
   */
  private void emailsIsValid(String email) throws InvalidEmailException {
    if (!emailAddressPattern.matcher(email).find()) {
      throw new InvalidEmailException();
    }
  }

  /**
   * Checks if the email is unique.
   * @param email the email address of the new user given at registration
   * @throws InvalidEmailException if the email is already registered
   */
  private void emailIsUnique(String email) throws InvalidEmailException {
    Optional<User> user = userStorage.getUserByEmail(email);

    if (user.isPresent()) {
      throw new InvalidEmailException();
    }
  }

  /**
   * Checks if the given username meets the following requirements: <ul> <li> must not be empty
   * </li> </ul>
   * @param username the username to check
   * @throws InvalidUserNameException if the username is the empty string
   */
  private void usernameIsValid(String username) throws InvalidUserNameException {
    if (username.equals(StringUtils.EMPTY)) {
      throw new InvalidUserNameException();
    }
  }


  /**
   * Checks if the username is unique.
   * @param username the username of the new user given at registration
   * @throws InvalidUserNameException if the username is taken
   */
  private void usernameIsUnique(String username) throws InvalidUserNameException {
    Optional<User> user = userStorage.getUserByUserName(username);

    if (user.isPresent()) {
      throw new InvalidUserNameException();
    }
  }

  /**
   * Checks if the password meets the requirements: <ul> <li> minimum length of 8 characters </li>
   * <li> must contain at least one number </li> <li> must contain at least one uppercase letter.
   * </li> </ul>
   * @param password the password of the new user given at registration
   * @throws InvalidPasswordException if the password does not meet the requirements
   */
  private void passwordIsValid(String password) throws InvalidPasswordException {
    if (!(password.length() >= PASSWORD_MIN_LENGTH
        && hasAtLeastOneNumberPattern.matcher(password).find()
        && hasAtLeastOneUppercaseLetterPattern.matcher(password).find())) {
      throw new InvalidPasswordException();
    }
  }
}
