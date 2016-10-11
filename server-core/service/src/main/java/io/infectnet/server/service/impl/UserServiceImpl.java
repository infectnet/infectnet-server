package io.infectnet.server.service.impl;

import io.infectnet.server.service.TokenService;
import io.infectnet.server.service.UserDTO;
import io.infectnet.server.service.UserService;

import javax.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Inject
    private TokenService tokenService;
    
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
        return false;
    }
}
