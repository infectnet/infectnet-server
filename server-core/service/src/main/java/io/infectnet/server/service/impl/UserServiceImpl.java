package io.infectnet.server.service.impl;

import io.infectnet.server.service.UserDTO;
import io.infectnet.server.service.UserService;

public class UserServiceImpl implements UserService {

  @Override
  public UserDTO register(String token, String username, String password) {
    return null;
  }

  @Override
  public UserDTO login(String username, String password) {
    return null;
  }

  @Override
  public boolean exists(UserDTO user) {
    return false;
  }
}
