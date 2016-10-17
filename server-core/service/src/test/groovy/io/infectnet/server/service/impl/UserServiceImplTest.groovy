package io.infectnet.server.service.impl

import io.infectnet.server.persistence.Token
import io.infectnet.server.persistence.TokenStorage
import io.infectnet.server.persistence.User
import io.infectnet.server.persistence.UserStorage
import io.infectnet.server.service.ConverterService
import io.infectnet.server.service.UserDTO
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceImplTest extends Specification {

    def final TEST_USERNAME_1 = "test username"

    def final TEST_EMAIL_1 = "test@email.com"

    def final TEST_PASSWORD_1 = "testPassword1"

    def final TEST_REGISTRATION_DATE = LocalDateTime.now()

    def final TEST_USERNAME_2 = "test username2"

    def final TEST_EMAIL_2 = "test2@email.com"

    def final TEST_PASSWORD_2 = "testPassword2"

    def final NEW_USERNAME = "new username"

    def final NEW_EMAIL = "new@email.com"

    def final NEW_PASSWORD = "newPassword0"

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
}
