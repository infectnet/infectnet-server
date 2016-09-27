package io.infectnet.server.service;

import java.util.List;

public interface TokenService {

    Token createNew();

    boolean exists(Token token);

    void delete(Token token);

    List<Token> getAllTokens();

}
