package io.infectnet.server.service.user.mapping

import io.infectnet.server.persistence.user.User
import io.infectnet.server.service.user.UserDTO
import spock.lang.Specification

import java.time.LocalDateTime

class UserDtoConverterImplTest extends Specification {

  def final TEST_USERNAME = "test_username"
  def final TEST_EMAIL = "test@email.com"
  def final TEST_PASSWORD = "test_pwd"
  def final TEST_REGISTRATION_DATE = LocalDateTime.now()

  def userDtoConverter

  def setup() {
    userDtoConverter = new UserDtoConverterImpl()
  }

  def "converting User entity to UserDTO"() {
    given: "a User entity"
      def userEntity = new User(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD, TEST_REGISTRATION_DATE)
      def expectedUserDto = new UserDTO(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD, TEST_REGISTRATION_DATE)

    expect: "the converted object is equal to the expected UserDTO"
      userDtoConverter.convert(userEntity) == expectedUserDto

  }

}
