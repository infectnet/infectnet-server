package io.infectnet.server.service.impl

import io.infectnet.server.persistence.Token
import io.infectnet.server.persistence.TokenStorage
import io.infectnet.server.service.TokenDTO
import org.modelmapper.ModelMapper
import spock.lang.Specification

import java.time.LocalDateTime

class TokenServiceImplTest extends Specification {

    def final EXPIRE_MINUTES = 10
    def final TOKEN_LENGTH = 16

    def final TEST_TOKEN_1 = "test_token_1"
    def final TEST_TOKEN_2 = "test_token_2"
    def final TEST_TOKEN_EXPIRATION_DATE = LocalDateTime.now()

    def tokenStorage
    def tokenService
    def modelMapper

    def setup() {
        modelMapper = Mock(ModelMapper)
        tokenStorage = Mock(TokenStorage)
        tokenService = new TokenServiceImpl(tokenStorage, modelMapper)
    }


    def "new token can be created"() {

        given: "there is no colliding token in the storage"
            tokenStorage.getTokenByTokenString(_) >> Optional.empty()

        when: "we create a new token"
            def result = tokenService.createNewToken()

        then: "the new token is valid and will be saved"
            1 * tokenStorage.saveToken(_)

            result.getToken().length() == TOKEN_LENGTH
            result.getExpirationDateTime().minusMinutes(EXPIRE_MINUTES).getMinute() == LocalDateTime.now().getMinute()

    }


    def "token exists in the storage"() {

        given: "there is a token in the storage"
            def tokenDto = new TokenDTO(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            def tokenEntity = new Token(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            modelMapper.map(tokenDto, Token.class) >> tokenEntity
            1 * tokenStorage.exists(tokenEntity) >> true

        expect: "the service detects the token is existant"
            tokenService.exists(tokenDto)

    }

    def "checking if null token exists"() {
        when: "we check if null token exists"
            tokenService.exists(null)
        then: "NullPointerException is thrown"
            thrown(NullPointerException)
    }

    def "token can be deleted"() {

        given: "the token we want to delete"
            def tokenDto = new TokenDTO(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            def tokenEntity = new Token(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            1 * modelMapper.map(tokenDto, Token.class) >> tokenEntity

        when: "we delete the token"
            tokenService.delete(tokenDto)

        then: "the token will be deleted from storage"
            1 * tokenStorage.deleteToken(tokenEntity)
    }

    def "deleting null token"() {
        when: "we want to delete null token"
            tokenService.delete(null)

        then: "NullPointerException is thrown"
            thrown(NullPointerException)
    }

    def "all tokens can be retrieved"() {

        given: "there is data in the storage"
            def expectedList = [
                    new TokenDTO(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE),
                    new TokenDTO(TEST_TOKEN_2, TEST_TOKEN_EXPIRATION_DATE)
            ]
            def entityList = [
                    new Token(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE),
                    new Token(TEST_TOKEN_2, TEST_TOKEN_EXPIRATION_DATE)
            ]
            1 * tokenStorage.getAllTokens() >> entityList
            2 * modelMapper.map(_, TokenDTO.class) >>> expectedList

        expect: "we get all of the tokens"
            tokenService.getAllTokens() == expectedList

    }

    def "no tokens can be retrieved"() {

        given: "there is no data in the storage"
            tokenStorage.getAllTokens() >> []

        expect: "we get an empty list"
            tokenService.getAllTokens().size() == 0
    }

    def "a token can be retrieved by tokenString"() {

        given: "the desired token is in the storage"
            def tokenEntity = new Token(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            def tokenDto = new TokenDTO(TEST_TOKEN_1, TEST_TOKEN_EXPIRATION_DATE)
            1 * tokenStorage.getTokenByTokenString(TEST_TOKEN_1) >> Optional.of(tokenEntity)
            1 * modelMapper.map(tokenEntity, TokenDTO.class) >> tokenDto

        expect: "we get an Optional containing the requested token"
            tokenService.getTokenByTokenString(TEST_TOKEN_1).get().getToken() == TEST_TOKEN_1
    }

    def "a token cannot be retrieved by tokenString"() {

        given: "the desired token is not in the storage"
            1 * tokenStorage.getTokenByTokenString(TEST_TOKEN_1) >> Optional.empty()

        expect: "we get an empty Optional"
            !tokenService.getTokenByTokenString(TEST_TOKEN_1).isPresent()
    }

    def "retrieving a token by null tokenString"() {
        when: "we want to retrieve a token with null tokenString"
            tokenService.getTokenByTokenString(null)

        then: "NullPointerException is thrown"
            thrown(NullPointerException)
    }

}