package io.infectnet.server.service.user.mapping;

import io.infectnet.server.persistence.user.User;
import io.infectnet.server.service.converter.Converter;
import io.infectnet.server.service.user.UserDTO;

/**
 * Implements converting from {@link UserDTO} to {@link User}.
 */
public class UserEntityConverterImpl implements Converter<UserDTO, User> {

  @Override
  public User convert(UserDTO dto) {
    return new User(
        dto.getUserName(),
        dto.getEmail(),
        dto.getPassword(),
        dto.getRegistrationDate()
    );
  }

  @Override
  public Class<UserDTO> getSourceClass() {
    return UserDTO.class;
  }

  @Override
  public Class<User> getTargetClass() {
    return User.class;
  }
}
