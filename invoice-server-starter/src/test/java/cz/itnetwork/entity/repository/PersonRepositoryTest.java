package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.helpers.DataMaker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    private DataMaker dataMaker;

    @BeforeEach
    public void setUp() {
        this.dataMaker = new DataMaker();
    }

    @Test
     void testSavePerson() {
        // Act
        PersonEntity savedPerson = personRepository.save(dataMaker.createPerson());

        // Assertions
        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getId()).isPositive();
    }

    @Test
     void testFindAll() {
        // Prepare data and save
        personRepository.saveAll(dataMaker.createPersons(2));
        List<PersonEntity> list = personRepository.findAll();

        // Assertions
        Assertions.assertThat(list).isNotNull().hasSize(2);
    }

    @Test
     void testFindById() {
        // Prepare data
        PersonEntity person1 = dataMaker.createPerson();

        // Act
        personRepository.saveAndFlush(person1);

        // Get result for assertions
        PersonEntity person = personRepository.findById(person1.getId()).get();

        // Assertions
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getId()).isPositive();
    }

    @Test
     void testFindByHidden() {
        // Prepare data
        PersonEntity person = dataMaker.createPerson();
        person.setHidden(true);

        // Act
        personRepository.saveAndFlush(person);
        List<PersonEntity> hiddenPersons = personRepository.findByHidden(true);

        // Assertions
        Assertions.assertThat(hiddenPersons).isNotNull().hasSize(1);
    }

    @Test
     void testFindByIdentificationNumber() {
        // Prepare data
        PersonEntity person = dataMaker.createPerson();
        String identificationNumber = person.getIdentificationNumber();
        personRepository.saveAndFlush(person);

        // Act
        List<PersonEntity> list = personRepository.findByIdentificationNumber(identificationNumber);
        List<PersonEntity> emptyList = personRepository.findByIdentificationNumber("asdas");

        // Assertions
        Assertions.assertThat(list).isNotNull().hasSize(1);
        Assertions.assertThat(emptyList).isEmpty();
    }
}
