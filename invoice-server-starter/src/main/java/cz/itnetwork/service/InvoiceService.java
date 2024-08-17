package cz.itnetwork.service;
//region imports
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.dto.statisticsDTO.InvoiceStatistics;

import java.util.List;
//endregion

public interface InvoiceService {
    /**
     * Add invoice to database
     * @param invoiceDTO contains body for future invoice entity
     * @return saved entity
     */
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    /**
     * Removes invoice from database
     * @param id used to find exact invoice to remove
     */
    void removeInvoice(Long id);

    /**
     * Fetches list of invoices
     * @param invoiceFilter could contain possible filters
     * @return filtered(or not) list of invoices
     */
    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);

    /**
     * Fetches invoice from database
     * @param id used to find exact invoice
     * @return invoiceDTO
     */
    InvoiceDTO getInvoice(Long id);

    /**
     * Updates existing invoice
     * @param id used to find exact invoice
     * @param invoiceDTO contains body with updates we want to apply
     * @return updated entity converted to DTO
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     * Fetches statistics
     * @return invoiceStatisticsDTO
     */
    InvoiceStatistics getStatistics();

    /**
     * Fetches list of invoices
     * @param id used to fetch invoices
     * @return list of invoice DTOs
     */
    List<InvoiceDTO> getInvoicesByPersonId(Long id);
}
