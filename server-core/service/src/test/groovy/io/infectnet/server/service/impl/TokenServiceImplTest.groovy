package io.infectnet.server.service.impl

import io.infectnet.server.persistence.Token
import io.infectnet.server.persistence.TokenStorage
import io.infectnet.server.service.TokenDTO
import spock.lang.Specification

import java.time.LocalDateTime

class TokenServiceImplTest extends Specification {

    def final TEST_TOKEN = "test_token"

    def final TEST_TOKEN_EXPIRATION_DATE = LocalDateTime.now()

    def tokenStorage
    def tokenService

    def setup() {
        tokenStorage = Mock(TokenStorage)
        tokenService = new TokenServiceImpl(tokenStorage)
    }

    def "token exists in the storage"() {

        given: "there is an example token in the TokenStorage"
        tokenStorage.getAllTokens() >> [new Token(TEST_TOKEN, TEST_TOKEN_EXPIRATION_DATE)]
        def token = new TokenDTO(TEST_TOKEN, TEST_TOKEN_EXPIRATION_DATE)


        expect: "the service detects the token is existant"
        tokenService.exists(token)

    }

    def "asd"() {

    }

}
