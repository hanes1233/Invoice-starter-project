package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import cz.itnetwork.dto.statisticsDTO.InvoiceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    // region Dependency injections
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper personMapper;
    // endregion

    /**
     * Add new invoice to database
     * @param invoiceDTO = invoice body with data
     * @return new created invoice with id, converted to DTO
     */
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        invoiceDTO.setSeller(personService.getPerson(invoiceDTO.getSeller().getId()));
        invoiceDTO.setBuyer(personService.getPerson(invoiceDTO.getBuyer().getId()));
        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        invoiceRepository.save(invoiceEntity);
        return invoiceMapper.toDTO(invoiceEntity);
    }

    /**
     * Remove invoice from database if exists
     * @param id = find invoice by id
     */
    @Override
    public void removeInvoice(Long id) {
        if(invoiceRepository.existsById(id))
            invoiceRepository.deleteById(id);
    }

    /**
     * Get list of all invoices from database
     * @param invoiceFilter = optional filter parameters
     * @return list of invoices after applying filters
     */
    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification specification = new InvoiceSpecification(invoiceFilter);
        return invoiceRepository
                .findAll(specification, PageRequest.of(0,invoiceFilter.getLimit()))
                .stream()
                .map(invoiceEntity -> invoiceMapper.toDTO(invoiceEntity))
                .collect(Collectors.toList());
    }

    /**
     * Get invoice from database
     * @param id used to find invoice
     * @return invoice converted to DTO
     */

    @Override
    public InvoiceDTO getInvoice(Long id) {
        return invoiceMapper.toDTO(
                invoiceRepository
                .findById(id)
                .orElseThrow());
    }

    /**
     * Update existing invoice
     * @param id = find invoice by id
     * @param invoiceDTO = contains invoice body with changes we want to apply
     * @return updated invoice converted to DTO
     */

    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        // Setting id to DTO
        invoiceDTO.setId(id);

        // Find invoice entity by id
        InvoiceEntity invoiceEntity = invoiceRepository.getReferenceById(id);

        // Update entity with invoiceDTO from parameter
        invoiceMapper.updateInvoiceEntity(invoiceDTO,invoiceEntity);

        // Set seller to invoice entity
        invoiceEntity.setSeller(personMapper.toEntity(personService.getPerson(invoiceDTO.getSeller().getId())));

        // Set buyer to invoice entity
        invoiceEntity.setBuyer(personMapper
                .toEntity(personService
                        .getPerson(invoiceDTO.getBuyer().getId())));

        // Save updated entity to databse
        invoiceRepository.saveAndFlush(invoiceEntity);

        return invoiceMapper.toDTO(invoiceEntity);
    }

    /**
     * Getting statistics from database : current year total revenue, all-time revenue
     * and count of invoices actually exist in database
     * @return new statistic's object
     */
    @Override
    public InvoiceStatistics getStatistics() {
            return new InvoiceStatistics(
                invoiceRepository.getCurrentYearSum(Date.valueOf(LocalDate.now().getYear()+"-01-01")),
                invoiceRepository.getAllTimeSum(),
                invoiceRepository.getInvoicesCount()
               );
    }

    /**
     * Get list of invoices from database
     * @param id = user's id to check for all his purchases and sales
     * @return list of invoices by user's id
     */
    @Override
    public List<InvoiceDTO> getInvoicesByPersonId(Long id) {
        // map list of entities to DTO
        return invoiceRepository.findAllByPersonId(id)
                .stream()
                .map(invoiceEntity -> invoiceMapper.toDTO(invoiceEntity))
                .toList();
    }
}
