package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.persistence.TokenStorage;
import io.infectnet.server.persistence.User;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.service.UserDTO;
import io.infectnet.server.service.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {

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

    /**
     * Registers the new user in. Checks if the token has not expired yet and the username is unique,
     * and whether or not the password is valid according to the specified rules:
     *      minimum length of 8 characters
     *      must contain at least one number
     *      must contain at least one uppercase letter.
     * If authentication fails no new user will be stored, otherwise a new User is stored
     * with the username, the email, the password and the date of the registration now produced.
     *
     * @param token the token from the current registration
     * @param email the email address from the current registration
     * @param username the username from the current registration
     * @param password the password from the current registration
     * @return the Optional containing the UserDTO made from the created user
     */
    @Override
    public Optional<UserDTO> register(String token, String email, String username, String password) {
        if(tokenIsValid(token) && emailIsUnique(email) && usernameIsUnique(username) && passwordIsValid(password)){
            UserDTO newUser = new UserDTO(username,email,password, LocalDateTime.now());
            User user = converterService.map(newUser, User.class);
            userStorage.saveUser(user);
            return Optional.of(newUser);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userStorage.getUserByUserName(username);
        if(user.isPresent() && user.get().getPassword().equals(password)){
            UserDTO userDTO = converterService.map(user.get(),UserDTO.class);
            return Optional.of(userDTO);
        }else{
            return Optional.empty();
        }
    }

    @Override
    public boolean exists(UserDTO user) {
        return userStorage.exists(converterService.map(Objects.requireNonNull(user), User.class));

    }

    /**
     * Checks if the token given is in the storage and not yet expired.
     *
     * @param token the token to check
     * @return true if the token is stored and valid, otherwise false
     */
    private boolean tokenIsValid(String token) {
        Optional<Token> foundToken = tokenStorage.getTokenByTokenString(token);
        return foundToken.isPresent();
                //&& foundToken.get().getExpirationDateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES)<=tokenService.getExpirationLimit());
    }

    private boolean emailIsUnique(String email) {
        Optional<User> user = userStorage.getUserByEmail(email);
        return !user.isPresent();
    }

    private boolean usernameIsUnique(String username) {
        Optional<User> user = userStorage.getUserByUserName(username);
        return !user.isPresent();
    }

    private boolean passwordIsValid(String password) {
        return (password.length()>=8
                    && password.matches(".*\\d+.*")
                    && password.matches(".*[A-Z]+.*"));
    }
}
