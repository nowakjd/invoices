package pl.coderstrust.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.database.Database;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class InvoiceService {

  private Database database;
  private static Logger log = LoggerFactory.getLogger(InvoiceService.class);

  InvoiceService(Database database) {
    this.database = database;
  }

  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    try {
      Invoice savedInvoice = database.save(invoice);
      log.info("Invoice saved.");
      return savedInvoice;
    } catch (Exception exception) {
      log.error("Invoice is not saved.", exception);
      throw exception;
    }
  }

  public void delete(Long id) throws DatabaseOperationException {
    try {
      database.delete(id);
      log.info("Invoice of id:{} was deleted.", id);
    } catch (DatabaseOperationException exception) {
      log.error("Invoice of id:{} is not deleted.", id, exception);
      throw exception;
    }
  }

  public Collection<Invoice> findAll() throws DatabaseOperationException {
    try {
      Collection<Invoice> allInvoices = database.findAll();
      log.info("Returned all invoices from database.");
      return allInvoices;
    } catch (DatabaseOperationException exception) {
      log.error("Failed to get all invoices", exception);
      throw exception;
    }
  }

  public Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException {
    try {
      Collection<Invoice> oneBuyerInvoices = database.findByBuyer(companyId);
      log.info("Returned all invoices from one buyer.");
      return oneBuyerInvoices;
    } catch (DatabaseOperationException exception) {
      log.error("Failed to get invoices from one buyer.");
      throw exception;
    }
  }

  public Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException {
    try {
      Collection<Invoice> oneSellerInvoices = database.findBySeller(companyId);
      log.info("Returned all invoices from one seller.");
      return oneSellerInvoices;
    } catch (DatabaseOperationException exception) {
      log.error("Failed to get invoices from one seller.");
      throw exception;
    }
  }

  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    try {
      Collection<Invoice> invoicesByDataRange = database.findByDate(fromDate, toDate);
      log.info("Returned all invoices by data range.");
      return invoicesByDataRange;
    } catch (DatabaseOperationException exception) {
      log.error("Failed to get invoices by data range.");
      throw exception;
    }
  }

  public Invoice findOne(Long id) throws DatabaseOperationException {
    try {
      Invoice invoiceById = database.findOne(id);
      log.info("Returned invoice of id:{}.", id);
      return invoiceById;
    } catch (DatabaseOperationException exception) {
      log.error("Invoice of id:{} not returned.", id, exception);
      throw exception;
    }
  }
}
