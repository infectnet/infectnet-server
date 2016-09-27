package io.infectnet.server.service;

import java.util.List;

public interface TokenService {

    TokenDTO createNew();

    boolean exists(TokenDTO token);

    void delete(TokenDTO token);

    List<TokenDTO> getAllTokens();

}
