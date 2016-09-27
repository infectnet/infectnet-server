package io.infectnet.server.persistence.impl;

import io.infectnet.server.persistence.User;
import io.infectnet.server.persistence.UserStorage;

import java.util.List;

public class InMemoryUserStorageImpl implements UserStorage {

    @Override
    public List<User> getAllUsers() {
        // To be implemented...
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        // To be implemented...
        return null;
    }

    @Override
    public void saveUser(User user) {
        // To be implemented...
    }

    @Override
    public void deleteUser(User user) {
        // To be implemented...
    }
}
