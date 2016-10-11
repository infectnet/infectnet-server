package io.infectnet.server.persistence;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    /**
     * Returns all registered users.
     *
     * @return a list of users
     */
    List<User> getAllUsers();

    /**
     * Returns the {@link User} with the given username.
     *
     * @param userName the username of the user
     * @return an {@link Optional} containing the user
     */
    Optional<User> getUserByUserName(String userName);

    /**
     * Returns the {@link User} with the given email.
     *
     * @param email the email address of the user
     * @return an {@link Optional} containing the user
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Persists a {@link User} in the storage.
     *
     * @param user the user to be saved
     */
    void saveUser(User user);

    /**
     * Deletes a {@link User} from the storage.
     *
     * @param user the user to be deleted
     */
    void deleteUser(User user);

}
