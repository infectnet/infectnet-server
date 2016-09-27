package io.infectnet.server.persistence;

import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User getUserById(Integer id);

    void saveUser(User user);

    void deleteUser(User user);

}
