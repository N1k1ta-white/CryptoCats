package cryptocats.backend.mapper;

import cryptocats.backend.dto.UserDto;
import cryptocats.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "tgName", source = "username")
    @Mapping(target = "lastOpenedTime", source = "lastOpenedTime", qualifiedByName = "timestampToInstant")
    User dtoToUser(UserDto userDto);

    @Mapping(target = "username", source = "tgName")
    @Mapping(target = "lastOpenedTime", source = "lastOpenedTime", qualifiedByName = "instantToTimestamp")
    UserDto userToDto(User user);

    @Named("instantToTimestamp")
    static Long instantToTimestamp(Instant instant) {
        return instant.toEpochMilli() / 1000;
    }

    @Named("timestampToInstant")
    static Instant timestampToInstant(Long timestamp) {
        if (timestamp == null) {
            return Instant.EPOCH;
        }
        return Instant.ofEpochSecond(timestamp);
    }
}
