package cz.itnetwork.controller;

//region imports
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.filter.InvoiceFilter;
import cz.itnetwork.service.InvoiceService;
import cz.itnetwork.service.PersonService;
import cz.itnetwork.dto.statisticsDTO.InvoiceStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//endregion

@RestController
@RequestMapping("/api")
public class InvoiceController {
    //region Dependency injections
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PersonService personService;
    //endregion

    @GetMapping({"/invoices","/invoices/"})
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter invoiceFilter) {
        return invoiceService.getAll(invoiceFilter);
    }

    @GetMapping({"/invoices/{id}","/invoices/{id}/"})
    public InvoiceDTO getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoice(id);
    }

    @PostMapping({"/invoices","/invoices/"})
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    @PutMapping({"/invoices/{id}","/invoices/{id}/"})
    public InvoiceDTO updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.updateInvoice(id,invoiceDTO);
    }

    @DeleteMapping({"/invoices/{id}","/invoices/{id}/"})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.removeInvoice(id);
    }

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSalesByIN(@PathVariable String identificationNumber) {
        return personService.getSales(identificationNumber);
   }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getPurchasesByIN(@PathVariable String identificationNumber) {
        return personService.getPurchases(identificationNumber);
    }

    @GetMapping({"invoices/statistics","invoices/statistics/"})
    public InvoiceStatistics getStatistics() {
        return invoiceService.getStatistics();
    }

    @GetMapping({"invoices/person/{id}","invoices/person/{id}/"})
    public List<InvoiceDTO> getInvoicesByPersonId(@PathVariable Long id) {
        return invoiceService.getInvoicesByPersonId(id);
    }

}
