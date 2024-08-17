package cz.itnetwork.service;
//region imports
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.dto.statisticsDTO.PersonStatistics;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;
//endregion

@Service
public class PersonServiceImpl implements PersonService {
    // region Dependency injections
    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;
    // endregion

    /**
     * Add new person to database
     * @param personDTO Person to create
     * @return new entity with id, converted to DTO
     */
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);

        return personMapper.toDTO(entity);
    }

    /**
     * Hide person in database
     * @param personId Person to hide
     */
    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    /**
     * Get list of all persons from database
     * @return list of entities converted to DTO
     */
    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPerson(Long id) {
        return personMapper.toDTO(fetchPersonById(id));
    }

    /**
     * Get list of purchases
     * @param identificationNumber used to fetch all person's purchases from database
     * @return list of purchases converted to DTO
     */
    @Override
    public List<InvoiceDTO> getPurchases(String identificationNumber) {
        return personRepository.findByIdentificationNumber(identificationNumber)
                .stream()
                .flatMap(list -> list.getPurchases().stream())
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Get list of sales
     * @param identificationNumber used to fetch all person's sales from database
     * @return list of sales converted to DTO
     */
    @Override
    public List<InvoiceDTO> getSales(String identificationNumber) {
        return personRepository.findByIdentificationNumber(identificationNumber)
                .stream()
                .flatMap(list -> list.getSales().stream())
                .map(i -> invoiceMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Update existing person entity in database
     * @param id = find person by id
     * @param personDTO = updates we want to apply
     * @return = addPerson function, which will replace existing entity with updated ones
     * and return updated entity converted to DTO
     */

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        PersonEntity personEntity = fetchPersonById(id);
        personEntity.setHidden(true);
        personRepository.save(personEntity);
        personDTO.setId(null);
        return addPerson(personDTO);
    }

    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */

    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }

    /**
     * Get person statistics
     * @return person's statistics list
     */
    @Override
    public List<PersonStatistics> getStatistics() {
        return invoiceRepository.findSellerIds().stream().map(id ->
                        new PersonStatistics(
                                id,
                                personRepository.findById(id).get().getName(),
                                invoiceRepository.findRevenueById(id)))
                .toList();
    }
}
