package cz.itnetwork.service;
//region imports
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.statisticsDTO.PersonStatistics;

import java.util.List;
//endregion

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

    /**
     * Fetch person
     * @param id used to fetch concrete person from database
     * @return personDTO
     */
    PersonDTO getPerson(Long id);

    /**
     * Fetches list of invoices by buyer's identification number
     * @param identificationNumber used to find invoices
     * @return list of invoices
     */
    List<InvoiceDTO> getPurchases(String identificationNumber);

    /**
     * Fetches list of invoices by seller's identification number
     * @param identificationNumber used to find invoices
     * @return list of invoices
     */
    List<InvoiceDTO> getSales(String identificationNumber);

    /**
     * Fetches person entity and update it
     * @param id used to find person in database
     * @param personDTO - body with our updates we want to apply
     * @return updated person DTO
     */
    PersonDTO updatePerson(Long id, PersonDTO personDTO);

    /**
     * Fetch person statistics
     * @return list of statistics for each person
     */
    List<PersonStatistics> getStatistics();
}
