package io.infectnet.server.service;

import java.util.Optional;

public interface UserService {

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
    Optional<UserDTO> register(String token, String username, String password);

    /**
     * Validates the user login, if valid username and password is given, then
     * returns the UserDTO created from the User found in the storage. If the
     * authentication fails, an empty Optional is returned.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the Optional containing a UserDTO made from the found user
     */
    Optional<UserDTO> login(String username, String password);

    /**
     * Checks if the user exists in storage.
     *
     * @param user the user to check
     * @return true if the user exists, false otherwise
     */
    boolean exists(UserDTO user);

}
