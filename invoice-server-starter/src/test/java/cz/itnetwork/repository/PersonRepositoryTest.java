package cz.itnetwork.repository;

import cz.itnetwork.EntityTest;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
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
public class PersonRepositoryTest {

    private PersonEntity person1;
    private PersonEntity person2;
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        EntityTest.definePersons();
        this.person1 = EntityTest.getPerson1();
        this.person2 = EntityTest.getPerson2();
    }

    @Test
    public void PersonRepository_Save_ReturnSavedPerson() {
        PersonEntity savedPerson = personRepository.save(person1);

        Assertions.assertThat(savedPerson).isNotNull();
        Assertions.assertThat(savedPerson.getId()).isGreaterThan(0);
    }

    @Test
    public void PersonRepository_GetAll_ReturnListOfPersons() {
        personRepository.saveAndFlush(person1);
        personRepository.saveAndFlush(person2);
        List<PersonEntity> list = personRepository.findAll();

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void PersonRepository_FindById_ReturnPerson() {
        personRepository.saveAndFlush(person1);
        PersonEntity person = personRepository.findById(person1.getId()).get();

        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getId()).isGreaterThan(0);
    }

    @Test
    public void PersonRepository_FindByHidden_ReturnPerson() {
        person1.setHidden(true);
        personRepository.saveAndFlush(person1);

        List<PersonEntity> hiddenPersons = personRepository.findByHidden(true);

        Assertions.assertThat(hiddenPersons).isNotNull();
        Assertions.assertThat(hiddenPersons.size()).isEqualTo(1);
    }

    @Test
    public void PersonRepository_FindByIdentificationNumber_ReturnPerson() {
        personRepository.saveAndFlush(person1);

        List<PersonEntity> list = personRepository.findByIdentificationNumber("199345");
        List<PersonEntity> emptyList = personRepository.findByIdentificationNumber("asdas");

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(emptyList).isEmpty();
    }
}
