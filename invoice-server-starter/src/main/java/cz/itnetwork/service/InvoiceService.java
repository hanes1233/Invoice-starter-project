package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.statistics.InvoiceStatistics;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);
    void removeInvoice(Long id);
    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);
    InvoiceDTO getInvoice(Long id);
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);
    InvoiceStatistics getStatistics();
    List<InvoiceDTO> getInvoicesByPersonId(Long id);
}
