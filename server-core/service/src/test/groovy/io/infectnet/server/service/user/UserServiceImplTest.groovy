package io.infectnet.server.service.user

import io.infectnet.server.persistence.token.Token
import io.infectnet.server.persistence.token.TokenStorage
import io.infectnet.server.persistence.user.User
import io.infectnet.server.persistence.user.UserStorage
import io.infectnet.server.service.converter.ConverterService
import io.infectnet.server.service.user.exception.InvalidEmailException
import io.infectnet.server.service.user.exception.InvalidPasswordException
import io.infectnet.server.service.user.exception.InvalidTokenException
import io.infectnet.server.service.user.exception.InvalidUserNameException
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceImplTest extends Specification {

    def final TEST_USERNAME_1 = "test username"

    def final TEST_EMAIL_1 = "test@email.com"

    def final TEST_PASSWORD_1 = "testPassword1"

    def final TEST_REGISTRATION_DATE = LocalDateTime.now()

    def final TEST_USERNAME_2 = "test username2"

    def final TEST_EMAIL_2 = "test2@email.com"

    def final INVALID_PASSWORD = "invpw"

    def final TEST_PASSWORD_2 = "testPassword2"

    def final TEST_TOKEN = "testToken1234567"

    def final TEST_VALID_EXPIRATION_DATE = LocalDateTime.now().plusMinutes(5)

    def userStorage
    def tokenStorage
    def userService
    def converterService

    def setup() {
        converterService = Mock(ConverterService)
        userStorage = Mock(UserStorage)
        tokenStorage = Mock(TokenStorage)
        userService = new UserServiceImpl(userStorage, tokenStorage, converterService)
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

        expect: "the service detects the user is valid"
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

        when: "a user registers in with valid data"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "the new user will be saved in the storage"
            1 * userStorage.saveUser(user)
            1 * tokenStorage.deleteToken(token)
    }

    def "new user tries to register in with expired token"(){
        given: "there is no valid token with given string but no conflicting user in the storage"
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.empty()
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        when: "a user registers in with invalid token"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidTokenException is thrown"
            thrown(InvalidTokenException)
    }

    def "new user tries to register in with invalid username"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

        when: "a user registers in with invalid token"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidUserNameException is thrown"
            thrown(InvalidUserNameException)
    }

    def "new user tries to register in with invalid email"(){
        given: "there is a valid token but a conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.of(user)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()

        when: "a user registers in with invalid token"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,TEST_PASSWORD_1)

        then: "InvalidEmailException is thrown"
            thrown(InvalidEmailException)
    }

    def "new user tries to register in with invalid password"(){
        given: "there is a valid token but conflicting user in the storage"
            def token = new Token(TEST_TOKEN,TEST_VALID_EXPIRATION_DATE)
            tokenStorage.getTokenByTokenString(TEST_TOKEN) >> Optional.of(token)
            userStorage.getUserByEmail(TEST_EMAIL_1) >> Optional.empty()
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.empty()
        when: "a user registers in with invalid token"
            userService.register(TEST_TOKEN,TEST_EMAIL_1,TEST_USERNAME_1,INVALID_PASSWORD)

        then: "InvalidPasswordException is thrown"
            thrown(InvalidPasswordException)
    }

    def "registered user logs in"(){
        given : "a registered user in storage"
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)
            def userDTO = new UserDTO(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)

            1 * converterService.map(user, UserDTO) >> userDTO

        expect: "the user logs in"
            userService.login(TEST_USERNAME_1, TEST_PASSWORD_1).equals(Optional.of(userDTO))
    }

    def "user tries to log in with valid username but invalid password"(){
        given : "a registered user in storage with given username but different password"
            def user = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.getUserByUserName(TEST_USERNAME_1) >> Optional.of(user)

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
