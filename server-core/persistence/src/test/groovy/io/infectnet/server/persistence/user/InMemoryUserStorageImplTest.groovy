package io.infectnet.server.persistence.user

import spock.lang.Specification

import java.time.LocalDateTime

class InMemoryUserStorageImplTest extends Specification {

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

    def userStorage = new InMemoryUserStorageImpl()

    def "all users can be retrieved"() {

        given: "there is data in the storage"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def secondUser = new User(TEST_USERNAME_2,TEST_EMAIL_2,TEST_PASSWORD_2,TEST_REGISTRATION_DATE)
            def expectedList = [firstUser, secondUser]
            userStorage.saveUser(firstUser)
            userStorage.saveUser(secondUser)

        expect: "we get all of the users"
            userStorage.getAllUsers() == expectedList

    }

    def "a user can be retrieved by userName"() {

        given: "the desired user is in the storage"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)

        expect: "we get an Optional containing the user"
            userStorage.getUserByUserName(TEST_USERNAME_1).get().getUserName() == TEST_USERNAME_1

    }

    def "a user cannot be retrieved, the given username does not match any in the storage"() {

        given: "other users are in the storage"
            def otherUser = new User(TEST_USERNAME_1, TEST_EMAIL_1, TEST_PASSWORD_1, TEST_REGISTRATION_DATE)
            userStorage.saveUser(otherUser)

        expect: "we get an empty Optional"
            userStorage.getUserByUserName(TEST_USERNAME_2).isPresent() == false
    }

    def "a user can be retrieved by email"() {

        given: "the desired user is in the storage"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)

        expect: "we get an Optional containing the user"
            userStorage.getUserByEmail(TEST_EMAIL_1).get().getEmail() == TEST_EMAIL_1

    }

    def "a user cannot be retrieved, the given email does not match any in the storage"() {

        given: "other users are in the storage"
            def otherUser = new User(TEST_USERNAME_1, TEST_EMAIL_1, TEST_PASSWORD_1, TEST_REGISTRATION_DATE)
            userStorage.saveUser(otherUser)

        expect: "we get an empty Optional"
            !userStorage.getUserByEmail(TEST_EMAIL_2).isPresent()
    }

    def "the user can be saved"(){

        given : "there is data in the storage and a new to be saved"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def secondUser = new User(TEST_USERNAME_2,TEST_EMAIL_2,TEST_PASSWORD_2,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)
            userStorage.saveUser(secondUser)

            def newUser = new User(NEW_USERNAME,NEW_EMAIL,NEW_PASSWORD,TEST_REGISTRATION_DATE)

        when : "we want to save the new user"
            userStorage.saveUser(newUser)

        then : "the new user will be in the storage"
            userStorage.exists(newUser)
    }

    def "user cannot be saved because the username is not unique"(){

        given : "a user in the storage and a new user to be saved"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)

            def newUser = new User(TEST_USERNAME_1,NEW_EMAIL,NEW_PASSWORD,TEST_REGISTRATION_DATE)

        when : "we want to save the new user with a conflicting username"
            userStorage.saveUser(newUser)

        then : "IllegalArgumentException will be thrown"
            thrown(IllegalArgumentException)
    }

    def "user cannot be saved because the email is not unique"(){

        given : "a user in the storage and a new user to be saved"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)

            def newUser = new User(NEW_USERNAME,TEST_EMAIL_1,NEW_PASSWORD,TEST_REGISTRATION_DATE)

        when : "we want to save the new user with a conflicting email"
            userStorage.saveUser(newUser)

        then : "IllegalArgumentException will be thrown"
            thrown(IllegalArgumentException)
    }

    def "user can be deleted"(){
        given : "users in the storage and a user to be deleted"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def secondUser = new User(TEST_USERNAME_2,TEST_EMAIL_2,TEST_PASSWORD_2,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)
            userStorage.saveUser(secondUser)

            def userToBeDeleted = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)

        when : "we want to delete the existing user"
            userStorage.deleteUser(userToBeDeleted)

        then : "the user will not be in the storage"
            !userStorage.exists(userToBeDeleted)
    }

    def "user cannot be deleted because it is not in the storage"(){

        given : "user in the storage and a user to be deleted"
            def firstUser = new User(TEST_USERNAME_1,TEST_EMAIL_1,TEST_PASSWORD_1,TEST_REGISTRATION_DATE)
            def userToBeDeleted = new User(TEST_USERNAME_2,TEST_EMAIL_2,TEST_PASSWORD_2,TEST_REGISTRATION_DATE)
            userStorage.saveUser(firstUser)

        when : "we want to delete the user"
            userStorage.deleteUser(userToBeDeleted)

        then : "no Exception is thrown"
    }
}