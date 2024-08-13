package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.service.statistics.PersonStatistics;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);

    /**
     * Fetches all non-hidden persons
     *
     * @return List of all non-hidden persons
     */
    List<PersonDTO> getAll();
    PersonDTO getPerson(Long id);
    List<InvoiceDTO> getPurchases(String indentificationNumber);
    List<InvoiceDTO> getSales(String indentificationNumber);
    PersonDTO updatePerson(Long id, PersonDTO personDTO);
    PersonDTO findSeller(Long id);
    PersonDTO findBuyer(Long id);
    List<PersonStatistics> getStatistics();

}
