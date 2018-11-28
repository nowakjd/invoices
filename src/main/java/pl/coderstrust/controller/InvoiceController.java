package pl.coderstrust.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

import java.time.LocalDate;
import java.util.Collection;

@RequestMapping("/invoices")
@RestController
public class InvoiceController {

  private InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @PostMapping
  public Invoice save(@RequestBody Invoice invoice) throws DatabaseOperationException {
    return invoiceService.save(invoice);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id)
      throws DatabaseOperationException {
    invoiceService.delete(id);
  }

  @GetMapping
  Collection<Invoice> findAll() throws DatabaseOperationException {
    return invoiceService.findAll();
  }

  Collection<Invoice> findByBuyer(Long companyId)
      throws DatabaseOperationException {
    return invoiceService.findByBuyer(companyId);
  }

  Collection<Invoice> findBySeller(Long companyId)
      throws DatabaseOperationException {
    return invoiceService.findBySeller(companyId);
  }

  @GetMapping("/byDate")
  Collection<Invoice> findByDate(@DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate fromDate,
      @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate toDate)
      throws DatabaseOperationException {
    return invoiceService.findByDate(fromDate, toDate);
  }

  @GetMapping("/{id}")
  Invoice findOne(@PathVariable Long id)
      throws DatabaseOperationException {
    return invoiceService.findOne(id);
  }
}
