package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.helpers.DataMaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PersonRepository personRepository;
    private DataMaker dataMaker;

    @BeforeEach
    public void setUp() {
        this.dataMaker = new DataMaker();
    }

    @Test
     void testSaveInvoice() {
        // Prepare data
        InvoiceEntity invoiceEntity = invoiceRepository.saveAndFlush(dataMaker.createInvoice());

        // Assertions
        Assertions.assertThat(invoiceEntity).isNotNull();
        Assertions.assertThat(invoiceEntity.getId()).isPositive();
    }

    @Test
     void testFindAll() {
        // Prepare data
        invoiceRepository.saveAll(dataMaker.createInvoices(2));
        List<InvoiceEntity> list = invoiceRepository.findAll();

        // Assertions
        Assertions.assertThat(list).isNotNull().hasSize(2);
    }

    @Test
     void testGetStatistics() {
        // Prepare data
        List<InvoiceEntity> invoiceEntityList = dataMaker.createInvoices(2);
        invoiceEntityList.get(0).setIssued(Date.valueOf("1994-02-28"));
        invoiceRepository.saveAll(invoiceEntityList);
        Integer sum = (int) (invoiceEntityList.get(0).getPrice() + invoiceEntityList.get(1).getPrice());

        // Act
        Integer futureYearSum = invoiceRepository.getCurrentYearSum(Date.valueOf("2025-01-01"));
        Integer currentYearSum = invoiceRepository.getCurrentYearSum(Date.valueOf("2021-11-01"));
        Integer totalSum = invoiceRepository.getAllTimeSum();

        // Assertions
        Assertions.assertThat(futureYearSum).isNull();
        Assertions.assertThat(totalSum).isEqualTo(sum);
        Assertions.assertThat((long)currentYearSum).isEqualTo(invoiceEntityList.get(1).getPrice());
    }

    @Test
     void testGetInvoiceCount() {
        // Prepare data
        Integer nullCount = invoiceRepository.getInvoicesCount();
        Iterable<InvoiceEntity> iterable = dataMaker.createInvoices(2);

        // Act
        invoiceRepository.saveAll(iterable);
        Integer count = invoiceRepository.getInvoicesCount();

        // Assertions
        Assertions.assertThat(nullCount).isZero();
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
     void getSellerIds() {
        // Check for repository being empty before data was set up
        Assertions.assertThat(invoiceRepository.findSellerIds()).isEmpty();

        // Prepare data
        InvoiceEntity invoice1 = dataMaker.createInvoice();
        InvoiceEntity invoice2 = dataMaker.createInvoice();
        invoice1.setSeller(personRepository.save(dataMaker.createPerson()));
        invoice2.setSeller(personRepository.save(dataMaker.createPerson()));
        invoiceRepository.saveAll(List.of(invoice1, invoice2));

        // Act
        List<Long> ids = invoiceRepository.findSellerIds();

        // Assertions
        Assertions.assertThat(ids)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
     void testFindRevenue() {
        // Check for repository being empty before data was set up
        Long zeroRevenue = invoiceRepository.findRevenueById(1L);

        // Prepare data
        PersonEntity person = personRepository.save(dataMaker.createPerson());
        InvoiceEntity invoice1 = dataMaker.createInvoice();
        InvoiceEntity invoice2 = dataMaker.createInvoice();
        invoice1.setSeller(person);
        invoice2.setSeller(person);

        // Act
        invoiceRepository.saveAll(List.of(invoice1, invoice2));
        Long totalRevenue = invoiceRepository.findRevenueById(invoice1.getSeller().getId());

        // Assertions
        Assertions.assertThat(zeroRevenue).isNull();
        Assertions.assertThat(totalRevenue).isNotNull().isEqualTo((invoice1.getPrice() + invoice2.getPrice()));
    }

    @Test
     void testFindAllInvoicesById() {
        // Prepare data
        PersonEntity person = personRepository.saveAndFlush(dataMaker.createPerson());
        InvoiceEntity invoice1 = dataMaker.createInvoice();
        InvoiceEntity invoice2 = dataMaker.createInvoice();
        invoice1.setSeller(person);
        invoice2.setSeller(person);

        // Act
        List<InvoiceEntity> emptyList = invoiceRepository.findAllByPersonId(1L);
        invoiceRepository.saveAll(List.of(invoice1, invoice2));
        List<InvoiceEntity> invoiceList = invoiceRepository.findAllByPersonId(person.getId());

        // Assertions
        Assertions.assertThat(emptyList).isEmpty();
        Assertions.assertThat(invoiceList).isNotEmpty().isNotNull().hasSizeGreaterThan(1);
    }
}
