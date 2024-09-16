package cryptocats.backend.mapper;

import cryptocats.backend.dto.OwnershipDto;
import cryptocats.backend.entity.Ownership;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnershipMapper {
    Ownership dtoToOwnership(OwnershipDto ownershipDto);

    OwnershipDto ownershipToDto(Ownership ownership);
}
