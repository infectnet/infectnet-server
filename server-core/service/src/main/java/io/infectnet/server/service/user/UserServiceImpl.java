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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private static final int PASSWORD_MIN_LENGTH = 8;

    private static final Pattern REGEX_AT_LEAST_ONE_NUMBER = Pattern.compile(".*\\d+.*");

    private static final Pattern REGEX_AT_LEAST_ONE_UPPERCASE_LETTER = Pattern.compile(".*[A-Z]+.*");

    private final UserStorage userStorage;

    private final TokenStorage tokenStorage;

    private final ConverterService converterService;

    private final EncrypterService encrypterService;

    public UserServiceImpl(UserStorage userStorage, TokenStorage tokenStorage, ConverterService converterService, EncrypterService encrypterService) {

        this.userStorage = userStorage;
        this.tokenStorage = tokenStorage;
        this.converterService = converterService;
        this.encrypterService = encrypterService;
    }

    @Override
    public UserDTO register(String token, String email, String username, String password) throws
        ValidationException {

            isRegisterValid( Objects.requireNonNull(token),
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

        if(user.isPresent()
                && encrypterService.check(password, user.get().getPassword())){

            UserDTO userDTO = converterService.map(user.get(),UserDTO.class);

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
     *
     * @param token the token from the current registration
     * @param email the email address from the current registration
     * @param username the username from the current registration
     * @param password the password from the current registration
     * @return true if all is valid, false otherwise
     */
    private void isRegisterValid(String token, String email, String username, String password) throws ValidationException {
        ValidationException firstException = null;

        try{
            tokenIsValid(token);
        }catch (InvalidTokenException e){
            e.setNextException(firstException);
            firstException = e;
        }

        try{
            passwordIsValid(password);
        }catch (InvalidPasswordException e){
            e.setNextException(firstException);
            firstException = e;
        }

        try{
            emailIsUnique(email);
        }catch (InvalidEmailException e){
            e.setNextException(firstException);
            firstException = e;
        }

        try {
            usernameIsUnique(username);
        } catch (InvalidUserNameException e){
            e.setNextException(firstException);
            firstException = e;
        }
        if(firstException != null){
            throw firstException;
        }
    }

    /**
     * Checks if the token given is in the storage.
     *
     * @param token the token to check
     * @return true if the token is stored and valid, otherwise false
     */
    private void tokenIsValid(String token) throws InvalidTokenException {
        Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);

        if(!foundToken.isPresent()){
            throw new InvalidTokenException();
        }

    }

    /**
     * Checks if the email is unique.
     *
     * @param email the email address of the new user given at registration
     * @return true if the email cannot be found in the storage, otherwise false
     */
    private void emailIsUnique(String email) throws InvalidEmailException {
        Optional<User> user = userStorage.getUserByEmail(email);

        if(user.isPresent()){
            throw new InvalidEmailException();
        }
    }

    /**
     * Checks if the username is unique.
     *
     * @param username the username of the new user given at registration
     * @return true if the username cannot be found in the storage, otherwise false
     */
    private void usernameIsUnique(String username) throws InvalidUserNameException {
        Optional<User> user = userStorage.getUserByUserName(username);

        if(user.isPresent()){
            throw new InvalidUserNameException();
        }
    }

    /**
     * Checks if the password meets the requirements:
     *      minimum length of 8 characters
     *      must contain at least one number
     *      must contain at least one uppercase letter.
     *
     * @param password the password of the new user given at registration
     * @return true if the password is valid, otherwise false
     */
    private void passwordIsValid(String password) throws InvalidPasswordException {
        if(!(password.length()>= PASSWORD_MIN_LENGTH
                && REGEX_AT_LEAST_ONE_NUMBER.matcher(password).find()
                && REGEX_AT_LEAST_ONE_UPPERCASE_LETTER.matcher(password).find())){
            throw new InvalidPasswordException();
        }
    }
}
