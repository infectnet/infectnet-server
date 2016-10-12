package io.infectnet.server.service.impl.converter;

import io.infectnet.server.persistence.Token;
import io.infectnet.server.service.Converter;
import io.infectnet.server.service.TokenDTO;

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
