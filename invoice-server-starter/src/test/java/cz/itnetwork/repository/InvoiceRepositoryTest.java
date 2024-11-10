package cz.itnetwork.repository;

import cz.itnetwork.EntityTest;
import cz.itnetwork.constant.Countries;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
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
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PersonRepository personRepository;

    private InvoiceEntity invoice1;
    private InvoiceEntity invoice2;
    private PersonEntity person1;
    private PersonEntity person2;

    @BeforeEach
    public void setUp() {
        EntityTest.defineInvoices();
        EntityTest.definePersons();
        this.person1 = EntityTest.getPerson1();
        this.person2 = EntityTest.getPerson2();
        this.invoice1 = EntityTest.getInvoice1();
        this.invoice2 = EntityTest.getInvoice2();
    }

    private void saveAllInvoices() {
        Iterable<InvoiceEntity> iterable = List.of(invoice1, invoice2);
        invoiceRepository.saveAll(iterable);
    }

    @Test
    public void InvoiceRepository_Save_ReturnSavedInvoice() {
        InvoiceEntity invoiceEntity = invoiceRepository.saveAndFlush(invoice1);

        Assertions.assertThat(invoiceEntity).isNotNull();
        Assertions.assertThat(invoiceEntity.getId()).isGreaterThan(0);
    }

    @Test
    public void InvoiceRepository_GetAll_ReturnListOfInvoices() {
        this.saveAllInvoices();

        List<InvoiceEntity> list = invoiceRepository.findAll();

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void InvoiceRepository_GetStatistics_ReturnSum() {
        this.saveAllInvoices();

        Integer futureYearSum = invoiceRepository.getCurrentYearSum(Date.valueOf("2025-01-01"));
        Integer currentYearSum = invoiceRepository.getCurrentYearSum(Date.valueOf("2021-11-01"));
        Integer totalSum = invoiceRepository.getAllTimeSum();

        Assertions.assertThat(futureYearSum).isNull();
        Assertions.assertThat(totalSum).isEqualTo(60000);
        Assertions.assertThat(currentYearSum).isEqualTo(32000);
    }

    @Test
    public void InvoiceRepository_GetInvoiceCount_ReturnCount() {
        Integer nullCount = invoiceRepository.getInvoicesCount();
        this.saveAllInvoices();
        Integer count = invoiceRepository.getInvoicesCount();

        Assertions.assertThat(nullCount).isEqualTo(0);
        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    public void InvoiceRepository_GetSellersIds_ReturnListOfId() {
        Assertions.assertThat(invoiceRepository.findSellerIds()).isEmpty();

        invoice1.setSeller(personRepository.save(person1));
        invoice2.setSeller(personRepository.save(person2));
        this.saveAllInvoices();

        List<Long> ids = invoiceRepository.findSellerIds();

        Assertions.assertThat(ids).isNotNull();
        Assertions.assertThat(ids).isNotEmpty();
        Assertions.assertThat(ids.size()).isEqualTo(2);
    }

    @Test
    public void InvoiceRepository_FindRevenue_ReturnLong() {

        Long zeroRevenue = invoiceRepository.findRevenueById(1L);

        PersonEntity person = personRepository.save(person1);

        invoice1.setSeller(person);
        invoice2.setSeller(person);

        this.saveAllInvoices();

        Long totalRevenue = invoiceRepository.findRevenueById(invoice1.getSeller().getId());

        Assertions.assertThat(zeroRevenue).isNull();
        Assertions.assertThat(totalRevenue).isNotNull();
        Assertions.assertThat(totalRevenue).isEqualTo(60000);
    }

    @Test
    public void InvoiceRepository_FindAllInvoicesById_ReturnListOfInvoices() {

        PersonEntity person = personRepository.saveAndFlush(person2);

        invoice1.setSeller(person);
        invoice2.setSeller(person);

        List<InvoiceEntity> emptyList = invoiceRepository.findAllByPersonId(1L);

        this.saveAllInvoices();

        List<InvoiceEntity> invoiceList = invoiceRepository.findAllByPersonId(person.getId());

        Assertions.assertThat(emptyList).isEmpty();
        Assertions.assertThat(invoiceList).isNotEmpty();
        Assertions.assertThat(invoiceList).isNotNull();
        Assertions.assertThat(invoiceList.size()).isGreaterThan(1);
    }
}
