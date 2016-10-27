package io.infectnet.server.service.token.mapping

import io.infectnet.server.persistence.token.Token
import io.infectnet.server.service.token.TokenDTO
import spock.lang.Specification

import java.time.LocalDateTime

class TokenDtoConverterImplTest extends Specification {

  def final TEST_TOKEN = "test_token"
  def final TEST_EXPIRATION_DATE = LocalDateTime.now()
  
  def tokenDtoConverter

  def setup() {
    tokenDtoConverter = new TokenDtoConverterImpl()
  }

  def "converting Token entity to TokenDTO"() {
    given: "a Token entity"
      def tokenEntity = new Token(TEST_TOKEN, TEST_EXPIRATION_DATE)
      def expectedTokenDto = new TokenDTO(TEST_TOKEN, TEST_EXPIRATION_DATE)

    expect: "the converter object is equal to the expected TokenDTO"
      tokenDtoConverter.convert(tokenEntity) == expectedTokenDto
  }

}
