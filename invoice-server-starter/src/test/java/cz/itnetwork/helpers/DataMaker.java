package cz.itnetwork.helpers;

import cz.itnetwork.constant.Countries;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataMaker {


    public PersonEntity createPerson() {
        return PersonEntity.builder()
                .name(RandomStringUtils.random(5))
                .identificationNumber(RandomStringUtils.random(7))
                .taxNumber(RandomStringUtils.random(7))
                .accountNumber(RandomStringUtils.random(7))
                .bankCode(RandomStringUtils.random(7))
                .iban(RandomStringUtils.random(7))
                .telephone(RandomStringUtils.random(7))
                .mail(RandomStringUtils.random(7))
                .street(RandomStringUtils.random(7))
                .zip(RandomStringUtils.random(7))
                .city(RandomStringUtils.random(7))
                .country(Countries.CZECHIA)
                .note(RandomStringUtils.random(7))
                .build();
    }

    public List<PersonEntity> createPersons(int size) {
        List<PersonEntity> personEntityList = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            PersonEntity person = PersonEntity.builder()
                    .name(RandomStringUtils.random(5))
                    .identificationNumber(RandomStringUtils.random(7))
                    .taxNumber(RandomStringUtils.random(7))
                    .accountNumber(RandomStringUtils.random(7))
                    .bankCode(RandomStringUtils.random(7))
                    .iban(RandomStringUtils.random(7))
                    .telephone(RandomStringUtils.random(7))
                    .mail(RandomStringUtils.random(7))
                    .street(RandomStringUtils.random(7))
                    .zip(RandomStringUtils.random(7))
                    .city(RandomStringUtils.random(7))
                    .country(Countries.CZECHIA)
                    .note(RandomStringUtils.random(7))
                    .build();
            personEntityList.add(person);
        }
        return personEntityList;
    }

    public InvoiceEntity createInvoice() {
        return InvoiceEntity.builder()
                .invoiceNumber(ThreadLocalRandom.current().nextInt(5000,10000))
                .issued(Date.valueOf("2022-01-01"))
                .dueDate(Date.valueOf(LocalDate.now()))
                .product(RandomStringUtils.random(5))
                .price(ThreadLocalRandom.current().nextLong(20L, 1000L))
                .vat(ThreadLocalRandom.current().nextInt(500,20000))
                .note(RandomStringUtils.random(10))
                .buyer(null)
                .seller(null)
                .build();
    }

    public List<InvoiceEntity> createInvoices(int size) {
        List<InvoiceEntity> invoiceEntityList = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            InvoiceEntity invoiceEntity = InvoiceEntity.builder()
                    .invoiceNumber(ThreadLocalRandom.current().nextInt(5000,10000))
                    .issued(Date.valueOf("2022-01-01"))
                    .dueDate(Date.valueOf(LocalDate.now()))
                    .product(RandomStringUtils.random(5))
                    .price(ThreadLocalRandom.current().nextLong(20L, 1000L))
                    .vat(ThreadLocalRandom.current().nextInt(500,20000))
                    .note(RandomStringUtils.random(10))
                    .buyer(null)
                    .seller(null)
                    .build();
            invoiceEntityList.add(invoiceEntity);
        }
        return invoiceEntityList;
    }

    public PersonDTO createPersonDTO() {
        return PersonDTO.builder()
                .name(RandomStringUtils.random(5))
                .identificationNumber(RandomStringUtils.random(7))
                .taxNumber(RandomStringUtils.random(7))
                .accountNumber(RandomStringUtils.random(7))
                .bankCode(RandomStringUtils.random(7))
                .iban(RandomStringUtils.random(7))
                .telephone(RandomStringUtils.random(7))
                .mail(RandomStringUtils.random(7))
                .street(RandomStringUtils.random(7))
                .zip(RandomStringUtils.random(7))
                .city(RandomStringUtils.random(7))
                .country(Countries.CZECHIA)
                .note(RandomStringUtils.random(7))
                .build();
    }

    public InvoiceDTO createInvoiceDTO() {
        return InvoiceDTO.builder()
                .invoiceNumber(ThreadLocalRandom.current().nextInt(5000,10000))
                .issued(Date.valueOf("2022-01-01"))
                .dueDate(Date.valueOf(LocalDate.now()))
                .product(RandomStringUtils.random(5))
                .price(ThreadLocalRandom.current().nextLong(20L, 1000L))
                .vat(ThreadLocalRandom.current().nextInt(500,20000))
                .note(RandomStringUtils.random(10))
                .buyer(null)
                .seller(null)
                .build();
    }
}
