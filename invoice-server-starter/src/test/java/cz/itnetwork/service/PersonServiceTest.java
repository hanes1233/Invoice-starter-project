package cz.itnetwork.service;

import cz.itnetwork.EntityTest;
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
public class PersonServiceTest {

    private PersonEntity person1;
    private PersonEntity person2;
    private PersonDTO personDTO1;

    @BeforeEach
    public void setUp() {
        EntityTest.definePersons();
        EntityTest.definePersonDTO();

        this.person1 = EntityTest.getPerson1();
        this.person2 = EntityTest.getPerson2();
        this.personDTO1 = EntityTest.getPersonDTO1();
        //this.personDTO2 = EntityTest.getPersonDTO2();
    }

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

    @Test
    public void PersonService_addPerson_ReturnsPersonDTO() {
        when(personMapper.toEntity(Mockito.any(PersonDTO.class))).thenReturn(person1);
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(personDTO1);
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(person1);

        PersonDTO savedPerson = personService.addPerson(personDTO1);

        Assertions.assertThat(savedPerson).isNotNull();
    }

    @Test
    public void PersonService_getPersonById_ReturnsPersonDTO() {
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person1));
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(personDTO1);

        PersonDTO personDTO1 = personService.getPerson(1L);

        Assertions.assertThat(personDTO1).isNotNull();
        Assertions.assertThat(personDTO1.getName()).isEqualTo("Pavel");
        Assertions.assertThat(personDTO1.getIdentificationNumber()).isEqualTo("199345");

        Mockito.verify(personRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(personMapper, Mockito.times(1)).toDTO(Mockito.any(PersonEntity.class));
    }

    @Test
    public void PersonService_updatePerson_ReturnsPersonDTO() {
        personDTO1.setName("ASDAS");

        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person1));
        when(personMapper.toEntity(Mockito.any(PersonDTO.class))).thenReturn(person1);
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(person1);
        when(personMapper.toDTO(Mockito.any(PersonEntity.class))).thenReturn(personDTO1);

        PersonDTO result = personService.updatePerson(1L, personDTO1);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo(personDTO1.getName());
    }

    @Test
    public void PersonService_removePerson_void() {
        when(personRepository.save(Mockito.any(PersonEntity.class))).thenReturn(person1);
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(person1));

        assertAll(() -> {
            personService.removePerson(1);
        });
    }

    @Test
    public void PersonService_getAll_ReturnsListOfPersons() {
        List<PersonEntity> listOfPersons = List.of(person1, person2);
        when(personRepository.findAll()).thenReturn(listOfPersons);

        listOfPersons = personRepository.findAll();

        Assertions.assertThat(listOfPersons).isNotNull();
        Assertions.assertThat(listOfPersons.size()).isGreaterThan(0);
    }

    @Test
    public void PersonService_getPurchases_ReturnsListOfPurchases() {
        EntityTest.defineInvoicesDTO();
        EntityTest.defineInvoices();

        person1.setPurchases(List.of(EntityTest.getInvoice1(), EntityTest.getInvoice2()));

        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(EntityTest.getInvoiceDTO1());
        when(personRepository.findByIdentificationNumber("199345")).thenReturn(Collections.singletonList(person1));

        List<InvoiceDTO> listOfPurchases = personService.getPurchases("199345");

        Assertions.assertThat(listOfPurchases).isNotNull();
        Assertions.assertThat(listOfPurchases).isNotEmpty();
    }

    @Test
    public void PersonService_getSales_ReturnsListOfSales() {
        EntityTest.defineInvoicesDTO();
        EntityTest.defineInvoices();

        person1.setSales(List.of(EntityTest.getInvoice1(), EntityTest.getInvoice2()));

        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(EntityTest.getInvoiceDTO1());
        when(personRepository.findByIdentificationNumber("199345")).thenReturn(Collections.singletonList(person1));

        List<InvoiceDTO> listOfSales = personService.getSales("199345");

        Assertions.assertThat(listOfSales).isNotNull();
        Assertions.assertThat(listOfSales.size()).isEqualTo(2);
    }

    @Test
    public void PersonService_getStatistics_ReturnsListOfStatistics() {
        EntityTest.defineInvoices();
        EntityTest.getInvoice1().setSeller(person1);
        EntityTest.getInvoice2().setSeller(person2);
        List<Long> stats = List.of(1L, 2L);
        List<PersonStatistics> listOfStatistics;
        Long revenue = 100L;
        Long revenue1 = 200L;

        when(invoiceRepository.findSellerIds()).thenReturn(stats);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(personRepository.findById(2L)).thenReturn(Optional.of(person2));
        when(invoiceRepository.findRevenueById(1L)).thenReturn(revenue);
        when(invoiceRepository.findRevenueById(2L)).thenReturn(revenue1);

        listOfStatistics = personService.getStatistics();
        Assertions.assertThat(listOfStatistics).isNotNull();
        Assertions.assertThat(listOfStatistics).isNotEmpty();
    }

}
