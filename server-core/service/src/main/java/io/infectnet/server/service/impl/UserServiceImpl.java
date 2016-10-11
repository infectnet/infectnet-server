package io.infectnet.server.service.impl;

import io.infectnet.server.persistence.User;
import io.infectnet.server.persistence.UserStorage;
import io.infectnet.server.service.TokenService;
import io.infectnet.server.service.UserDTO;
import io.infectnet.server.service.UserService;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    private final ModelMapper modelMapper;

    @Inject
    private TokenService tokenService;

    public UserServiceImpl(UserStorage userStorage, TokenService tokenService, ModelMapper modelMapper) {
        this.userStorage = userStorage;
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
    }

    /**
     * Registers the new user in. Checks if the token has not expired yet and the username is unique,
     * and whether or not the password is valid according to the specified rules:
     *      minimum length of 8 characters
     *      must contain at least one number
     *      must contain at least one uppercase letter.
     * If authentication fails no new user will be stored, otherwise a new User is stored
     * with the username and password and the date of the registration now produced.
     *
     * @param token the token from the current registration
     * @param username the username from the current registration
     * @param password the password from the current registration
     * @return the Optional containing the UserDTO made from the created user
     */
    @Override
    public Optional<UserDTO> register(String token, String username, String password) {
        return null;
    }

    @Override
    public Optional<UserDTO> login(String username, String password) {
        return null;
    }

    @Override
    public boolean exists(UserDTO user) {
        return userStorage.exists(modelMapper.map(Objects.requireNonNull(user), User.class));

    }
}
