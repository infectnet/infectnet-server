package io.infectnet.server.persistence.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryUserStorageImpl implements UserStorage {

    private final List<User> userList;

    public InMemoryUserStorageImpl() {
        this.userList = new ArrayList<>();
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(userList);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userList.stream()
                .filter( user -> user.getUserName().equals(userName))
                .findFirst();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userList.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void saveUser(User user) {
        if(!getUserByEmail(user.getEmail()).isPresent()
                && !getUserByUserName(user.getUserName()).isPresent()){
            userList.add(user);
        }else{
            throw new IllegalArgumentException("The email or the name of the user is not unique!");
        }
    }

    @Override
    public void deleteUser(User user) {
        userList.remove(user);
    }

    @Override
    public boolean exists(User user) {
        return userList.stream()
                .filter(t -> t.getUserName().equals(user.getUserName()))
                .count() != 0;
    }
}
