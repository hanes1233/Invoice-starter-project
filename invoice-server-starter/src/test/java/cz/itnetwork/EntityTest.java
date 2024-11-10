package cz.itnetwork;

import cz.itnetwork.constant.Countries;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

public class EntityTest {
    @Getter
    @Setter
    private static PersonEntity person1;

    @Getter
    @Setter
    private static PersonEntity person2;

    @Getter
    @Setter
    private static PersonDTO personDTO1;

    @Getter
    @Setter
    private static PersonDTO personDTO2;

    @Getter
    @Setter
    private static InvoiceEntity invoice1;

    @Getter
    @Setter
    private static InvoiceEntity invoice2;

    @Getter
    @Setter
    private static InvoiceDTO invoiceDTO1;

    @Getter
    @Setter
    private static InvoiceDTO invoiceDTO2;


    public static void definePersons() {
        person1 = PersonEntity.builder()
                .name("Pavel")
                .identificationNumber("199345")
                .taxNumber("AI1234")
                .accountNumber("CZ1234567890")
                .bankCode("0600")
                .iban("IBAN0987654321")
                .telephone("+420774502960")
                .mail("pavelherasymov@seznam.cz")
                .street("Lazarska 123")
                .zip("123000")
                .city("Praha")
                .country(Countries.CZECHIA)
                .note("asdas")
                .build();

        person2 = PersonEntity.builder()
                .name("Olena")
                .identificationNumber("123123234")
                .taxNumber("ZXCZw1233")
                .accountNumber("UKR39874287")
                .bankCode("2440")
                .iban("IBAN948893489")
                .telephone("+420776193239")
                .mail("olenaherasymova@seznam.cz")
                .street("Pervomaiska 34324")
                .zip("3242342")
                .city("Praha")
                .country(Countries.CZECHIA)
                .note("xcvxv")
                .build();
    }

    public static void defineInvoices() {
        invoice1 = InvoiceEntity.builder()
                .invoiceNumber(123456)
                .issued(Date.valueOf("2022-05-26"))
                .dueDate(Date.valueOf("2024-01-01"))
                .product("Iphone")
                .price(32000L)
                .vat(325)
                .note("Buy new present")
                .buyer(null)
                .seller(null)
                .build();

        invoice2 = InvoiceEntity.builder()
                .invoiceNumber(987654)
                .issued(Date.valueOf("2020-02-16"))
                .dueDate(Date.valueOf("2023-11-05"))
                .product("Samsung")
                .price(28000L)
                .vat(325)
                .note("Buy new present")
                .buyer(null)
                .seller(null)
                .build();
    }

    public static void definePersonDTO() {
        personDTO1 = PersonDTO.builder()
                .name("Pavel")
                .identificationNumber("199345")
                .taxNumber("AI1234")
                .accountNumber("CZ1234567890")
                .bankCode("0600")
                .iban("IBAN0987654321")
                .telephone("+420774502960")
                .mail("pavelherasymov@seznam.cz")
                .street("Lazarska 123")
                .zip("123000")
                .city("Praha")
                .country(Countries.CZECHIA)
                .note("asdas")
                .build();

        personDTO2 = PersonDTO.builder()
                .name("Olena")
                .identificationNumber("123123234")
                .taxNumber("ZXCZw1233")
                .accountNumber("UKR39874287")
                .bankCode("2440")
                .iban("IBAN948893489")
                .telephone("+420776193239")
                .mail("olenaherasymova@seznam.cz")
                .street("Pervomaiska 34324")
                .zip("3242342")
                .city("Praha")
                .country(Countries.CZECHIA)
                .note("xcvxv")
                .build();
    }

    public static void defineInvoicesDTO() {
        invoiceDTO1 = InvoiceDTO.builder()
                .invoiceNumber(2123456)
                .issued(Date.valueOf("2022-05-26"))
                .dueDate(Date.valueOf("2024-01-01"))
                .product("Iphone")
                .price(32000L)
                .vat(325)
                .note("Buy new present")
                .buyer(null)
                .seller(null)
                .build();

        invoiceDTO2 = InvoiceDTO.builder()
                .invoiceNumber(3987654)
                .issued(Date.valueOf("2020-02-16"))
                .dueDate(Date.valueOf("2023-11-05"))
                .product("Samsung")
                .price(28000L)
                .vat(325)
                .note("Buy new present")
                .buyer(null)
                .seller(null)
                .build();
    }
}
