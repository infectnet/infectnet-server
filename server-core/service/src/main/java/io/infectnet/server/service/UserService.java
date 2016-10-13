package io.infectnet.server.service;

public interface UserService {

  UserDTO register(String token, String username, String password);

  UserDTO login(String username, String password);

  boolean exists(UserDTO user);

}
