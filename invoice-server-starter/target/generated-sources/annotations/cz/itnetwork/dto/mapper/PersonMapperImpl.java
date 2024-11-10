package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.PersonEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonEntity toEntity(PersonDTO source) {
        if ( source == null ) {
            return null;
        }

        PersonEntity.PersonEntityBuilder personEntity = PersonEntity.builder();

        if ( source.getId() != null ) {
            personEntity.id( source.getId() );
        }
        personEntity.name( source.getName() );
        personEntity.identificationNumber( source.getIdentificationNumber() );
        personEntity.taxNumber( source.getTaxNumber() );
        personEntity.accountNumber( source.getAccountNumber() );
        personEntity.bankCode( source.getBankCode() );
        personEntity.iban( source.getIban() );
        personEntity.telephone( source.getTelephone() );
        personEntity.mail( source.getMail() );
        personEntity.street( source.getStreet() );
        personEntity.zip( source.getZip() );
        personEntity.city( source.getCity() );
        personEntity.country( source.getCountry() );
        personEntity.note( source.getNote() );

        return personEntity.build();
    }

    @Override
    public PersonDTO toDTO(PersonEntity source) {
        if ( source == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.id( source.getId() );
        personDTO.name( source.getName() );
        personDTO.identificationNumber( source.getIdentificationNumber() );
        personDTO.taxNumber( source.getTaxNumber() );
        personDTO.accountNumber( source.getAccountNumber() );
        personDTO.bankCode( source.getBankCode() );
        personDTO.iban( source.getIban() );
        personDTO.telephone( source.getTelephone() );
        personDTO.mail( source.getMail() );
        personDTO.street( source.getStreet() );
        personDTO.zip( source.getZip() );
        personDTO.city( source.getCity() );
        personDTO.country( source.getCountry() );
        personDTO.note( source.getNote() );

        return personDTO.build();
    }
}
