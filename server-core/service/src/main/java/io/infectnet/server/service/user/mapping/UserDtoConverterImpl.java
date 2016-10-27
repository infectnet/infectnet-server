package io.infectnet.server.service.user.mapping;

import io.infectnet.server.persistence.user.User;
import io.infectnet.server.service.converter.Converter;
import io.infectnet.server.service.user.UserDTO;

/**
 * Implements converting from {@link User} to {@link UserDTO}.
 */
public class UserDtoConverterImpl implements Converter<User, UserDTO> {

  @Override
  public UserDTO convert(User entity) {
    return new UserDTO(
        entity.getUserName(),
        entity.getEmail(),
        entity.getPassword(),
        entity.getRegistrationDate()
    );
  }

  @Override
  public Class<User> getSourceClass() {
    return User.class;
  }

  @Override
  public Class<UserDTO> getTargetClass() {
    return UserDTO.class;
  }
}
