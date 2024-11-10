package cz.itnetwork.service;

import cz.itnetwork.EntityTest;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    private InvoiceEntity invoice1;
    private InvoiceEntity invoice2;
    private InvoiceDTO invoiceDTO1;
    private InvoiceDTO invoiceDTO2;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private PersonServiceImpl personService;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;


    @BeforeEach
    public void setUp() {
        EntityTest.defineInvoices();
        EntityTest.defineInvoicesDTO();
        this.invoice1 = EntityTest.getInvoice1();
        this.invoice2 = EntityTest.getInvoice2();
        this.invoiceDTO1 = EntityTest.getInvoiceDTO1();
        this.invoiceDTO2 = EntityTest.getInvoiceDTO2();
    }

    @Test
    public void InvoiceService_addInvoice_ReturnsInvoiceDTO() {
        EntityTest.definePersonDTO();
        invoiceDTO1.setSeller(EntityTest.getPersonDTO1());
        invoiceDTO1.setBuyer(EntityTest.getPersonDTO2());
        invoiceDTO1.setId(1L);

        when(invoiceRepository.save(Mockito.any(InvoiceEntity.class))).thenReturn(invoice1);
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO1);
        when(invoiceMapper.toEntity(Mockito.any(InvoiceDTO.class))).thenReturn(invoice1);

        InvoiceDTO invoice = invoiceService.addInvoice(invoiceDTO1);
        Assertions.assertThat(invoice).isNotNull();
        Assertions.assertThat(invoice.getId()).isGreaterThan(0);
    }

    @Test
    public void InvoiceService_removeInvoice_void() {
        assertAll(() -> { invoiceService.removeInvoice(1L); });
    }

    @Test
    public void InvoiceService_getAll_ReturnsListOfDTO() {
        InvoiceFilter filter = InvoiceFilter.builder()
                .limit(2)
                .build();
        List<InvoiceEntity> invoiceEntities = List.of(invoice1, invoice2);
        Page<InvoiceEntity> page = new PageImpl<>(invoiceEntities, PageRequest.of(0, 2), invoiceEntities.size());

        when(invoiceRepository.findAll(Mockito.any(InvoiceSpecification.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);
        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO1);

        List<InvoiceDTO> listOfInvoices = invoiceService.getAll(filter);

        Assertions.assertThat(listOfInvoices).isNotNull();
        Assertions.assertThat(listOfInvoices.size()).isEqualTo(2);
    }

    @Test
    public void InvoiceService_getInvoice_ReturnsInvoiceDTO() {
        invoiceDTO1.setId(1L);

        when(invoiceMapper.toDTO(Mockito.any(InvoiceEntity.class))).thenReturn(invoiceDTO1);
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice1));

        InvoiceDTO invoiceResult = invoiceService.getInvoice(1L);
        Assertions.assertThat(invoiceResult).isNotNull();
        Assertions.assertThat(invoiceResult.getId()).isNotEqualTo(0);
    }

    @Test
    public void InvoiceService_updateInvoice_ReturnsInvoiceDTO() {
        when(invoiceMapper.updateInvoiceEntity(Mockito.any(InvoiceDTO.class), Mockito.any(InvoiceEntity.class))).thenReturn(invoice1);
        when(invoiceRepository.getReferenceById(1L)).thenReturn(invoice1);
    }

    @Test
    public void InvoiceService_getStatistics_ReturnsInvoiceStatistics() {}

    @Test
    public void InvoiceService_getInvoicesByPersonId_ListOfInvoiceDTO() {}
}
