package io.infectnet.server.persistence.impl

import io.infectnet.server.persistence.Token
import spock.lang.Specification

import java.time.LocalDateTime

class InMemoryTokenStorageImplTest extends Specification {

    public static final String TEST_TOKEN_2 = "test_token_2"
    public static final String NEW_TOKEN = "new_token"
    def final TOKEN_EXPIRE_DATE = LocalDateTime.now()
    def final TEST_TOKEN_1 = "test_token"

    def final INVALID_TOKEN = "invalid_token"

    def tokenStorage

    def "a token can be retrieved by tokenString"() {

        given: "the desired token is in the storage"
            def token = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            tokenStorage = new InMemoryTokenStorageImpl([token])

        expect: "we get an Optional containing the token"
            tokenStorage.getTokenByTokenString(TEST_TOKEN_1).get().getToken().equals(TEST_TOKEN_1)

    }

    def "a token can not be retrieved by tokenString"() {

        given: "the desired token is not in the storage"
            def token = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            tokenStorage = new InMemoryTokenStorageImpl([token])

        expect: "we get an empty Optional"
            !tokenStorage.getTokenByTokenString(INVALID_TOKEN).isPresent()

    }

    def "all tokens can be retrieved"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def expectedList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(expectedList)

        expect: "we get all of the tokens"
            tokenStorage.getAllTokens().equals(expectedList)

    }

    def "token exists in the storage"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def tokenList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(tokenList)

        expect: "the token we look for exists"
            tokenStorage.exists(new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE))


    }

    def "the token doesn't exists in the storage"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def tokenList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(tokenList)

        expect: "the token we look for doesn't exists"
            !tokenStorage.exists(new Token(INVALID_TOKEN, TOKEN_EXPIRE_DATE))


    }

    def "a token can be saved"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def tokenList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(tokenList)

            def newToken = new Token(NEW_TOKEN, TOKEN_EXPIRE_DATE)

        when: "we want to save a new token"
            tokenStorage.saveToken(newToken)

        then: "the token will be in the storage"
            tokenStorage.exists(newToken)

    }

    def "a token cannot be saved because of unique constraint"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def tokenList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(tokenList)

        when: "we want to save again an existing token"
            tokenStorage.saveToken(firstToken)

        then: "IllegalArgumentException is thrown"
            thrown(IllegalArgumentException)
    }

    def "a token can be deleted"() {

        given: "there is data in the storage"
            def firstToken = new Token(TEST_TOKEN_1, TOKEN_EXPIRE_DATE)
            def secondToken = new Token(TEST_TOKEN_2, TOKEN_EXPIRE_DATE)
            def tokenList = [firstToken, secondToken]
            tokenStorage = new InMemoryTokenStorageImpl(tokenList)

        when: "we want to delete an existing token"
            tokenStorage.deleteToken(firstToken)

        then: "the token no longer exists"
            !tokenStorage.exists(firstToken)

    }

}