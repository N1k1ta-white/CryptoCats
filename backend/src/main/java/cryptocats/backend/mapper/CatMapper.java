package cryptocats.backend.mapper;

import cryptocats.backend.dto.CatDto;
import cryptocats.backend.entity.Cat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatMapper {

    Cat dtoToCat(CatDto catDto);

    CatDto catToDto(Cat cat);
}
