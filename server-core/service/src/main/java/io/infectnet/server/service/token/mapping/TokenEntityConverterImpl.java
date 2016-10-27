package io.infectnet.server.service.token.mapping;

import io.infectnet.server.persistence.token.Token;
import io.infectnet.server.service.converter.Converter;
import io.infectnet.server.service.token.TokenDTO;

/**
 * Implements converting from {@link TokenDTO} to {@link Token}.
 */
public class TokenEntityConverterImpl implements Converter<TokenDTO, Token> {

  @Override
  public Token convert(TokenDTO source) {
    return new Token(source.getToken(), source.getExpirationDateTime());
  }

  @Override
  public Class<TokenDTO> getSourceClass() {
    return TokenDTO.class;
  }

  @Override
  public Class<Token> getTargetClass() {
    return Token.class;
  }
}
