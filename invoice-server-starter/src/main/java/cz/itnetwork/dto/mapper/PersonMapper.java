package cz.itnetwork.dto.mapper;

//region imports
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import org.mapstruct.Mapper;
//endregion

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonEntity toEntity(PersonDTO source);

    PersonDTO toDTO(PersonEntity source);
}
