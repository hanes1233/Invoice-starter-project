package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.dto.statisticsDTO.InvoiceStatistics;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import cz.itnetwork.helpers.DataMaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;
    private DataMaker dataMaker;


    @BeforeEach
    public void setUp() {
        this.dataMaker = new DataMaker();
    }

    @Test
     void saveInvoice() {
        // Prepare data
        InvoiceDTO invoiceDTO = dataMaker.createInvoiceDTO();
        invoiceDTO.setSeller(dataMaker.createPersonDTO());
        invoiceDTO.setBuyer(dataMaker.createPersonDTO());
        invoiceDTO.setId(1L);

        // Mock actions
        when(invoiceRepository.save(Mockito.any(InvoiceEntity.class))).thenReturn(dataMaker.createInvoice());
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO);
        when(invoiceMapper.toEntity(Mockito.any(InvoiceDTO.class))).thenReturn(dataMaker.createInvoice());

        // Act
        InvoiceDTO invoice = invoiceService.addInvoice(invoiceDTO);

        // Assertions
        Assertions.assertThat(invoice).isNotNull();
        Assertions.assertThat(invoice.getId()).isPositive();
    }

    @Test
     void removeInvoice() {
        assertAll(() -> { invoiceService.removeInvoice(1L); });
    }

    @Test
     void testFindAll() {
        // Prepare data
        InvoiceFilter filter = InvoiceFilter.builder()
                .limit(2)
                .build();
        List<InvoiceEntity> invoiceEntities = dataMaker.createInvoices(2);
        Page<InvoiceEntity> page = new PageImpl<>(invoiceEntities, PageRequest.of(0, 2), invoiceEntities.size());

        // Mock actions
        when(invoiceRepository.findAll(Mockito.any(InvoiceSpecification.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(dataMaker.createInvoiceDTO());

        // Act
        List<InvoiceDTO> listOfInvoices = invoiceService.getAll(filter);

        // Assertions
        Assertions.assertThat(listOfInvoices).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
     void testFindInvoiceById() {
        // Prepare data
        InvoiceDTO invoiceDTO = dataMaker.createInvoiceDTO();
        invoiceDTO.setId(1L);

        // Mock actions
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(dataMaker.createInvoice()));

        // Act
        InvoiceDTO invoiceResult = invoiceService.getInvoice(1L);

        // Assertions
        Assertions.assertThat(invoiceResult).isNotNull();
        Assertions.assertThat(invoiceResult.getId()).isNotZero();
    }

    @Test
     void updateInvoice() {
        // Prepare data
        PersonDTO personDTO = dataMaker.createPersonDTO();
        PersonDTO personDTO1 = dataMaker.createPersonDTO();
        personDTO.setId(2L);
        personDTO1.setId(3L);
        PersonEntity person = dataMaker.createPerson();
        person.setId(1L);
        InvoiceEntity invoiceEntity = dataMaker.createInvoice();
        invoiceEntity.setId(1L);
        InvoiceDTO invoiceDTO = dataMaker.createInvoiceDTO();
        invoiceDTO.setSeller(personDTO);
        invoiceDTO.setBuyer(personDTO1);

        // Mock actions
        when(invoiceRepository.getReferenceById(1L)).thenReturn(invoiceEntity);
        when(invoiceRepository.saveAndFlush(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceEntity);
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO);
        when(invoiceMapper.updateInvoiceEntity(Mockito.any(InvoiceDTO.class), Mockito.any(InvoiceEntity.class))).thenReturn(invoiceEntity);
        when(personMapper.toEntity(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(personService.getPerson(2L)).thenReturn(personDTO);

        // Act
        InvoiceDTO updatedDTO = invoiceService.updateInvoice(1L, invoiceDTO);

        // Assertions
        Assertions.assertThat(updatedDTO).isNotNull();
        Assertions.assertThat(updatedDTO.getId()).isEqualTo(1);
    }

    @Test
     void getStatistics() {
        when(invoiceRepository.getCurrentYearSum(Mockito.any(Date.class))).thenReturn(500000);
        when(invoiceRepository.getAllTimeSum()).thenReturn(200000);
        when(invoiceRepository.getInvoicesCount()).thenReturn(25000);

        InvoiceStatistics result = invoiceService.getStatistics();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getCurrentYearSum()).isEqualTo(500000);
        Assertions.assertThat(result.getAllTimeSum()).isGreaterThan(199090);
    }

    @Test
     void getInvoiceByPersonId() {

        // Mock actions
        when(invoiceRepository.findAllByPersonId(1L)).thenReturn(dataMaker.createInvoices(2));
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(dataMaker.createInvoiceDTO());

        // Act
        List<InvoiceDTO> listOfInvoices = invoiceService.getInvoicesByPersonId(1L);

        // Assertions
        Assertions.assertThat(listOfInvoices).isNotNull().isNotEmpty().hasSizeGreaterThan(1);
    }
}
