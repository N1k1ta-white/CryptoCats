package cryptocats.backend.mapper;

import cryptocats.backend.dto.PartialUserResponse;
import cryptocats.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartialUserResponseMapper {

    @Mapping(target = "username", source = "tgName")
    PartialUserResponse userToPartialUserResponse(User user);
}
