package cz.itnetwork.service;

import cz.itnetwork.helpers.DataMaker;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.dto.statisticsDTO.PersonStatistics;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class PersonServiceTest {

    private DataMaker dataMaker;

    @BeforeEach
    public void setUp() {
        this.dataMaker = new DataMaker();
    }
    // region Mocks
    @Mock
    private PersonMapper personMapper;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private PersonServiceImpl personService;
    // endregion

    @Test
     void testSavePerson() {
        // Mock actions
        when(personMapper.toEntity(Mockito.any(PersonDTO.class))).thenReturn(dataMaker.createPerson());
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(dataMaker.createPersonDTO());
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(dataMaker.createPerson());

        // Act
        PersonDTO savedPerson = personService.addPerson(dataMaker.createPersonDTO());

        // Assertions
        Assertions.assertThat(savedPerson).isNotNull();
    }

    @Test
     void testGetPersonById() {
        // Prepare data
        PersonEntity personEntity = dataMaker.createPerson();
        PersonDTO personDTO = dataMaker.createPersonDTO();
        personDTO.setName(personEntity.getName());
        personDTO.setIdentificationNumber(personEntity.getIdentificationNumber());

        // Mock actions
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(personDTO);

        // Act
        PersonDTO personDTO1 = personService.getPerson(1L);

        // Assertions
        Assertions.assertThat(personDTO1).isNotNull();
        Assertions.assertThat(personDTO1.getName()).isEqualTo(personEntity.getName());
        Assertions.assertThat(personDTO1.getIdentificationNumber()).isEqualTo(personEntity.getIdentificationNumber());

        // Verifications
        Mockito.verify(personRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(personMapper, Mockito.times(1)).toDTO(Mockito.any(PersonEntity.class));
    }

    @Test
     void testUpdatePerson() {
        // Prepare data
        PersonDTO personDTO = dataMaker.createPersonDTO();
        PersonEntity personEntity = dataMaker.createPerson();
        personEntity.setId(1L);

        // Mock actions
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(personMapper.toEntity(Mockito.any(PersonDTO.class))).thenReturn(dataMaker.createPerson());
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(personEntity);
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(personDTO);

        // Act
        PersonDTO result = personService.updatePerson(1L, personDTO);

        // Assertions
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(personDTO.getName());
    }

    @Test
     void testRemoveById() {
        // Mock actions
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(dataMaker.createPerson());
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(dataMaker.createPerson()));

        // Assertions
        assertAll(() -> {
            personService.removePerson(1);
        });
    }

    @Test
     void testFindAll() {
        // Mock action
        when(personRepository.findAll()).thenReturn(dataMaker.createPersons(2));

        // Act
        List<PersonEntity> listOfPersons = personRepository.findAll();

        // Assert
        Assertions.assertThat(listOfPersons).isNotNull().hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
     void getPurchases() {
        // Prepare data
        PersonEntity person = dataMaker.createPerson();
        person.setPurchases(dataMaker.createInvoices(2));

        // Mock actions
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(dataMaker.createInvoiceDTO());
        when(personRepository.findByIdentificationNumber(person.getIdentificationNumber())).thenReturn(Collections.singletonList(person));

        // Act
        List<InvoiceDTO> listOfPurchases = personService.getPurchases(person.getIdentificationNumber());

        // Assertions
        Assertions.assertThat(listOfPurchases).isNotNull().isNotEmpty();
    }

    @Test
     void testGetSales() {
        // Prepare data
        PersonEntity person = dataMaker.createPerson();
        person.setSales(dataMaker.createInvoices(2));

        // Mock actions
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(dataMaker.createInvoiceDTO());
        when(personRepository.findByIdentificationNumber("199345")).thenReturn(Collections.singletonList(person));

        // Act
        List<InvoiceDTO> listOfSales = personService.getSales("199345");

        // Assertions
        Assertions.assertThat(listOfSales).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
     void testGetStatistics() {
        // Prepare data
        InvoiceEntity invoiceEntity = dataMaker.createInvoice();
        InvoiceEntity invoiceEntity1 = dataMaker.createInvoice();
        invoiceEntity.setSeller(dataMaker.createPerson());
        invoiceEntity1.setSeller(dataMaker.createPerson());
        List<Long> stats = List.of(1L, 2L);
        List<PersonStatistics> listOfStatistics;
        Long revenue = 100L;
        Long revenue1 = 200L;

        // Mock actions
        when(invoiceRepository.findSellerIds()).thenReturn(stats);
        when(personRepository.findById(1L)).thenReturn(Optional.of(dataMaker.createPerson()));
        when(personRepository.findById(2L)).thenReturn(Optional.of(dataMaker.createPerson()));
        when(invoiceRepository.findRevenueById(1L)).thenReturn(revenue);
        when(invoiceRepository.findRevenueById(2L)).thenReturn(revenue1);

        // Act
        listOfStatistics = personService.getStatistics();

        // Assertions
        Assertions.assertThat(listOfStatistics).isNotNull().isNotEmpty();
    }

}
