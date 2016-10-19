package io.infectnet.server.service.token.mapping

import io.infectnet.server.persistence.token.Token
import io.infectnet.server.service.token.TokenDTO
import spock.lang.Specification

import java.time.LocalDateTime

class TokenEntityConverterImplTest extends Specification {

  def final TEST_TOKEN = "test_token"
  def final TEST_EXPIRATION_DATE = LocalDateTime.now()

  def tokenEntityConverter

  def setup() {
    tokenEntityConverter = new TokenEntityConverterImpl()
  }

  def "converting TokenDTO to Token entity"() {
    given: "a TokenDTO"
      def tokenDto = new TokenDTO(TEST_TOKEN, TEST_EXPIRATION_DATE)
      def expectedTokenEntity = new Token(TEST_TOKEN, TEST_EXPIRATION_DATE)

    expect: "the converted object is equal to the expected Token entity"
      tokenEntityConverter.convert(tokenDto) == expectedTokenEntity

  }

}
