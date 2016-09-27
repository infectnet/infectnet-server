package io.infectnet.server.persistence;

import java.util.List;

public interface TokenStorage {

    List<Token> getAllTokens();

    Token getTokenById(Integer id);

    void saveToken(Token token);

    void deleteToken(Token token);
}
