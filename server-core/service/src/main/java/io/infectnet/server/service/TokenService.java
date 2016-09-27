package io.infectnet.server.service;

import java.util.List;

public interface TokenService {

    TokenDTO createNewToken();

    boolean exists(TokenDTO token);

    void delete(TokenDTO token);

    List<TokenDTO> getAllTokens();

}
