package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.User;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.service.TokenDTO;
import io.infectnet.server.service.UserDTO;
import io.infectnet.server.service.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    public static final int PASSWORD_MIN_LENGTH = 8;

    public static final Pattern REGEX_AT_LEAST_ONE_NUMBER = Pattern.compile(".*\\d+.*");

    public static final Pattern REGEX_AT_LEAST_ONE_UPPERCASE_LETTER = Pattern.compile(".*[A-Z]+.*");

    private final UserStorage userStorage;

    private final TokenStorage tokenStorage;

    private final ConverterServiceImpl converterService;

    public UserServiceImpl(){
        converterService = null;
        tokenStorage = null;
        userStorage = null;
    }

    public UserServiceImpl(UserStorage userStorage, TokenStorage tokenStorage, ConverterServiceImpl converterService) {

        this.userStorage = userStorage;
        this.tokenStorage = tokenStorage;
        this.converterService = converterService;
    }

    @Override
    public Optional<UserDTO> register(String token, String email, String username, String password) {

        if(isRegisterValid( Objects.requireNonNull(token),
                            Objects.requireNonNull(email),
                            Objects.requireNonNull(username),
                            Objects.requireNonNull(password))
                ){

            UserDTO newUser = new UserDTO(username, email, password, LocalDateTime.now());

            User user = converterService.map(newUser, User.class);
            userStorage.saveUser(user);


            Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);
            tokenStorage.deleteToken(foundToken.get());

            return Optional.of(newUser);
        } else {

            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userStorage.getUserByUserName(Objects.requireNonNull(username));

        if(user.isPresent() && user.get().getPassword().equals(password)){
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
    private boolean isRegisterValid(String token, String email, String username, String password) {
        return tokenIsValid(token)
                && passwordIsValid(password)
                && emailIsUnique(email)
                && usernameIsUnique(username);
    }

    /**
     * Checks if the token given is in the storage.
     *
     * @param token the token to check
     * @return true if the token is stored and valid, otherwise false
     */
    private boolean tokenIsValid(String token) {
        Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);

        return foundToken.isPresent();
    }

    /**
     * Checks if the email is unique.
     *
     * @param email the email address of the new user given at registration
     * @return true if the email cannot be found in the storage, otherwise false
     */
    private boolean emailIsUnique(String email) {
        Optional<User> user = userStorage.getUserByEmail(email);

        return !user.isPresent();
    }

    /**
     * Checks if the username is unique.
     *
     * @param username the username of the new user given at registration
     * @return true if the username cannot be found in the storage, otherwise false
     */
    private boolean usernameIsUnique(String username) {
        Optional<User> user = userStorage.getUserByUserName(username);

        return !user.isPresent();
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
    private boolean passwordIsValid(String password) {
        return (password.length()>= PASSWORD_MIN_LENGTH
                && REGEX_AT_LEAST_ONE_NUMBER.matcher(password).find()
                && REGEX_AT_LEAST_ONE_UPPERCASE_LETTER.matcher(password).find());
    }
}
