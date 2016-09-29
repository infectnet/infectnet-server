package io.infectnet.server.persistence;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> getAllUsers();

    Optional<User> getUserByUserName(String userName);

    Optional<User> getUserByEmail(String email);

    void saveUser(User user);

    void deleteUser(User user);

}
