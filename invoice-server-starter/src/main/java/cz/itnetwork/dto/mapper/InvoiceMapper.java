package cz.itnetwork.dto.mapper;
//region imports
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
//endregion

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceDTO toDTO(InvoiceEntity source);

    InvoiceEntity toEntity(InvoiceDTO source);

    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    InvoiceEntity updateInvoiceEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);

}
