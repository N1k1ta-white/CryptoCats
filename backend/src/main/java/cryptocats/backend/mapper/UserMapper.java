package cryptocats.backend.mapper;

import cryptocats.backend.dto.UserDto;
import cryptocats.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);
}
