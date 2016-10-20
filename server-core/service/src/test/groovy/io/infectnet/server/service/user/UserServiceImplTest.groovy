package io.infectnet.server.service.user

import io.infectnet.server.persistence.token.Token
import io.infectnet.server.persistence.token.TokenStorage
import io.infectnet.server.persistence.user.User
import io.infectnet.server.persistence.user.UserStorage
import io.infectnet.server.service.converter.ConverterService
import io.infectnet.server.service.encrypt.EncrypterService
import io.infectnet.server.service.user.exception.InvalidEmailException
import io.infectnet.server.service.user.exception.InvalidPasswordException
import io.infectnet.server.service.user.exception.InvalidTokenException
import io.infectnet.server.service.user.exception.InvalidUserNameException
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceImplTest extends Specification {

    def final INVALID_TOKEN_MESSAGE = "InvalidToken"

    def final TOKEN_TARGET = "token"

    def final INVALID_PASSWORD_MESSAGE = "InvalidPassword"

    def final PASSWORD_TARGET = "password"

    def final INVALID_USERNAME_MESSAGE = "InvalidUserName"

    def final USERNAME_TARGET = "username"

    def final INVALID_EMAIL_MESSAGE = "InvalidEmail"

    def final EMAIL_TARGET = "email"

    def final TEST_USERNAME_1 = "test username"

    def final TEST_EMAIL_1 = "test@email.com"

    def final TEST_PASSWORD_1 = "testPassword1"

    def final TEST_REGISTRATION_DATE = LocalDateTime.now()

    def final TEST_USERNAME_2 = "test username2"

    def final TEST_EMAIL_2 = "test2@email.com"

    def final INVALID_PASSWORD = "invpw"

    def final TEST_PASSWORD_2 = "testPassword2"

    def final TEST_TOKEN = "testToken1234567"

    def final TEST_HASHED_PASSWORD_1 = "hashedPassword1"

    def final TEST_HASHED_PASSWORD_2 = "hashedPassword2"

    def final TEST_VALID_EXPIRATION_DATE = LocalDateTime.now().plusMinutes(5)

    def userStorage
    def tokenStorage
    def userService
    def converterService
    def encrypterService

    def setup() {
        converterService = Mock(ConverterService)
        userStorage = Mock(UserStorage)
        tokenStorage = Mock(TokenStorage)
        encrypterService = Mock(EncrypterService)
        userService = new UserServiceImpl(userStorage, tokenStorage, converterService, encrypterService)
    }


    def "user exists in the storage"() {
        given: "there is a user in the storage"
            def userDTO = new UserDTO(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def userEntity = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            converterService.map(userDTO, User.class) >> userEntity
            1 * userStorage.exists(userEntity) >> true

        expect: "the service detects the user is valid"
            userService.exists(userDTO)
    }

    def "the user does not exists in the storage"() {
        given: "there is a user in the storage"
            def userDTO = new UserDTO(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def userEntity = new User(TEST_USERNAME_2,TEST_EMAIL_2,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            converterService.map(userDTO, User.class) >> userEntity
            1 * userStorage.exists(userEntity) >> false

        expect: "the service detects the user is invalid"
            !userService.exists(userDTO)
    }

    def "new user registers with valid token, username, email and password"(){

        given: "there is a valid token and no conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

            1 * converterService.map(_ as UserDTO, User) >> user
            1 * encrypterService.hash(TEST_PASSWORD_1) >> TEST_HASHED_PASSWORD_1

        when: "a user registers with valid data"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "the new user will be saved in the storage"
            1 * userStorage.saveUser(user)
            1 * tokenStorage.deleteToken(token)
    }

    def "new user tries to register with expired token"(){
        given: "there is no valid token with given string but no conflicting user in the storage"
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.empty()
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        when: "a user registers with invalid token"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidTokenException is thrown"
            thrown(InvalidTokenException)
    }

    def "new user tries to register with invalid username"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

        when: "a user registers with invalid username"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidUserNameException is thrown"
            thrown(InvalidUserNameException)
    }

    def "new user tries to register with invalid email"(){
        given: "there is a valid token but a conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.of(user)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        when: "a user registers with invalid email"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidEmailException is thrown"
            thrown(InvalidEmailException)
    }

    def "new user tries to register with invalid password"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        when: "a user registers with invalid password"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,INVALID_PASSWORD)

        then: "InvalidPasswordException is thrown"
            thrown(InvalidPasswordException)
    }

    def "new user tries to register with invalid username and invalid password"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

        when: "a user registers with invalid password"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,INVALID_PASSWORD)

        then: "InvalidUserNameException is thrown with InvalidPasswordException chained to it"
            def ex = thrown(InvalidUserNameException)
            ex.getTarget() == USERNAME_TARGET
            ex.getMessage() == INVALID_USERNAME_MESSAGE
            def nextEx = ex.getNextException()
            nextEx.getTarget() == PASSWORD_TARGET
            nextEx.getMessage() == INVALID_PASSWORD_MESSAGE
    }

    def "new user tries to register with invalid username, invalid email and invalid password"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.of(user)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

        when: "a user registers with multiple invalid data"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,INVALID_PASSWORD)

        then: "InvalidUserNameException is thrown with InvalidEmailException and InvalidPasswordException chained to it"
            def ex = thrown(InvalidUserNameException)
            ex.getTarget() == USERNAME_TARGET
            ex.getMessage() == INVALID_USERNAME_MESSAGE
            def nextEx = ex.getNextException()
            nextEx.getTarget() == EMAIL_TARGET
            nextEx.getMessage() == INVALID_EMAIL_MESSAGE
            def secondChainedEx = nextEx.getNextException()
            secondChainedEx.getTarget() == PASSWORD_TARGET
            secondChainedEx.getMessage() == INVALID_PASSWORD_MESSAGE
    }

    def "new user tries to register with invalid all invalid data throws multiple Exception in a chain"(){
        given: "there is no valid token with given string and conflicting user in the storage"
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.empty()
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.of(user)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

        when: "a user registers with multiple invalid data"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,INVALID_PASSWORD)

        then: "InvalidUserNameException is thrown with InvalidEmailException and InvalidPasswordException and InvalidTokenException chained to it"
            def ex = thrown(InvalidUserNameException)
            ex.getTarget() == USERNAME_TARGET
            ex.getMessage() == INVALID_USERNAME_MESSAGE
            def nextEx = ex.getNextException()
            nextEx.getTarget() == EMAIL_TARGET
            nextEx.getMessage() == INVALID_EMAIL_MESSAGE
            def secondChainedEx = nextEx.getNextException()
            secondChainedEx.getTarget() == PASSWORD_TARGET
            secondChainedEx.getMessage() == INVALID_PASSWORD_MESSAGE
            def thirdChainedEx = secondChainedEx.getNextException()
            thirdChainedEx.getTarget() == TOKEN_TARGET
            thirdChainedEx.getMessage() == INVALID_TOKEN_MESSAGE
    }

    def "registered user logs in"(){
        given : "a registered user in storage"
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_HASHED_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)
            def userDTO = new UserDTO(TEST_USERNAME_1,TEST_EMAIL_1,TEST_HASHED_PASSWORD_1,TEST_REGISTRATION_DATE)

            1 * converterService.map(user, UserDTO) >> userDTO
            1 * encrypterService.check(TEST_PASSWORD_1, TEST_HASHED_PASSWORD_1) >> true

        expect: "the user logs in"
            userService.login(TEST_USERNAME_1, TEST_PASSWORD_1).equals(Optional.of(userDTO))
    }

    def "user tries to log in with valid username but invalid password"(){
        given : "a registered user in storage with given username but different password"
            def user = new User(TEST_USERNAME_1, TEST_EMAIL_1, TEST_HASHED_PASSWORD_1, TEST_REGISTRATION_DATE)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

            1 * encrypterService.check(TEST_PASSWORD_2, TEST_HASHED_PASSWORD_1) >> false
        
        expect: "the user logs in"
            userService.login(TEST_USERNAME_1, TEST_PASSWORD_2).equals(Optional.empty())
    }

    def "user tries to log in with invalid username"(){
        given : "a registered user in storage with given username but different password"
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        expect: "the user logs in"
            userService.login(TEST_USERNAME_1, TEST_PASSWORD_1).equals(Optional.empty())
    }
}
