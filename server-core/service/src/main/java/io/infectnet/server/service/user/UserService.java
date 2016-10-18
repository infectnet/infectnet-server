package io.infectnet.server.service.user;

import io.infectnet.server.service.user.exception.ValidationException;

import java.util.Optional;

public interface UserService {

    /**
     * Validates the new registration using the token, the username and the password.
     * If authentication fails no new user will be stored, otherwise a new User is created and stored
     * with the username, the email, the password and the date of the registration now produced.
     * Returns a UserDTO made from the new user.
     *
     * @param token the token from the current registration
     * @param email the email address from the current registration
     * @param username the username from the current registration
     * @param password the password from the current registration
     * @return the Optional containing the UserDTO made from the created user
     */
    UserDTO register(String token, String email, String username, String password) throws
        ValidationException;

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
