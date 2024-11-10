package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public InvoiceDTO toDTO(InvoiceEntity source) {
        if ( source == null ) {
            return null;
        }

        InvoiceDTO.InvoiceDTOBuilder invoiceDTO = InvoiceDTO.builder();

        invoiceDTO.id( source.getId() );
        invoiceDTO.invoiceNumber( source.getInvoiceNumber() );
        invoiceDTO.seller( personEntityToPersonDTO( source.getSeller() ) );
        invoiceDTO.buyer( personEntityToPersonDTO( source.getBuyer() ) );
        invoiceDTO.issued( source.getIssued() );
        invoiceDTO.dueDate( source.getDueDate() );
        invoiceDTO.product( source.getProduct() );
        invoiceDTO.price( source.getPrice() );
        invoiceDTO.vat( source.getVat() );
        invoiceDTO.note( source.getNote() );

        return invoiceDTO.build();
    }

    @Override
    public InvoiceEntity toEntity(InvoiceDTO source) {
        if ( source == null ) {
            return null;
        }

        InvoiceEntity.InvoiceEntityBuilder invoiceEntity = InvoiceEntity.builder();

        invoiceEntity.id( source.getId() );
        invoiceEntity.invoiceNumber( source.getInvoiceNumber() );
        invoiceEntity.issued( source.getIssued() );
        invoiceEntity.dueDate( source.getDueDate() );
        invoiceEntity.product( source.getProduct() );
        invoiceEntity.price( source.getPrice() );
        invoiceEntity.vat( source.getVat() );
        invoiceEntity.note( source.getNote() );
        invoiceEntity.buyer( personDTOToPersonEntity( source.getBuyer() ) );
        invoiceEntity.seller( personDTOToPersonEntity( source.getSeller() ) );

        return invoiceEntity.build();
    }

    @Override
    public InvoiceEntity updateInvoiceEntity(InvoiceDTO source, InvoiceEntity target) {
        if ( source == null ) {
            return target;
        }

        target.setId( source.getId() );
        target.setInvoiceNumber( source.getInvoiceNumber() );
        target.setIssued( source.getIssued() );
        target.setDueDate( source.getDueDate() );
        target.setProduct( source.getProduct() );
        target.setPrice( source.getPrice() );
        target.setVat( source.getVat() );
        target.setNote( source.getNote() );

        return target;
    }

    protected PersonDTO personEntityToPersonDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.id( personEntity.getId() );
        personDTO.name( personEntity.getName() );
        personDTO.identificationNumber( personEntity.getIdentificationNumber() );
        personDTO.taxNumber( personEntity.getTaxNumber() );
        personDTO.accountNumber( personEntity.getAccountNumber() );
        personDTO.bankCode( personEntity.getBankCode() );
        personDTO.iban( personEntity.getIban() );
        personDTO.telephone( personEntity.getTelephone() );
        personDTO.mail( personEntity.getMail() );
        personDTO.street( personEntity.getStreet() );
        personDTO.zip( personEntity.getZip() );
        personDTO.city( personEntity.getCity() );
        personDTO.country( personEntity.getCountry() );
        personDTO.note( personEntity.getNote() );

        return personDTO.build();
    }

    protected PersonEntity personDTOToPersonEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        PersonEntity.PersonEntityBuilder personEntity = PersonEntity.builder();

        if ( personDTO.getId() != null ) {
            personEntity.id( personDTO.getId() );
        }
        personEntity.name( personDTO.getName() );
        personEntity.identificationNumber( personDTO.getIdentificationNumber() );
        personEntity.taxNumber( personDTO.getTaxNumber() );
        personEntity.accountNumber( personDTO.getAccountNumber() );
        personEntity.bankCode( personDTO.getBankCode() );
        personEntity.iban( personDTO.getIban() );
        personEntity.telephone( personDTO.getTelephone() );
        personEntity.mail( personDTO.getMail() );
        personEntity.street( personDTO.getStreet() );
        personEntity.zip( personDTO.getZip() );
        personEntity.city( personDTO.getCity() );
        personEntity.country( personDTO.getCountry() );
        personEntity.note( personDTO.getNote() );

        return personEntity.build();
    }
}
