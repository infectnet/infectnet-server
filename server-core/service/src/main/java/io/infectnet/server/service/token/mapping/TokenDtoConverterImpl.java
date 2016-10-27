package io.infectnet.server.service.token.mapping;

import io.infectnet.server.persistence.token.Token;
import io.infectnet.server.service.converter.Converter;
import io.infectnet.server.service.token.TokenDTO;

/**
 * Implements converting from {@link Token} to {@link TokenDTO}.
 */
public class TokenDtoConverterImpl implements Converter<Token, TokenDTO> {

  @Override
  public TokenDTO convert(Token source) {
    return new TokenDTO(source.getToken(), source.getExpirationDateTime());
  }

  @Override
  public Class<Token> getSourceClass() {
    return Token.class;
  }

  @Override
  public Class<TokenDTO> getTargetClass() {
    return TokenDTO.class;
  }
}
