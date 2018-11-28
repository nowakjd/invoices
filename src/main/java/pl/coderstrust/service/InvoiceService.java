package pl.coderstrust.service;

import org.springframework.stereotype.Service;
import pl.coderstrust.database.Database;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class InvoiceService {

  private Database database;

  InvoiceService(Database database) {
    this.database = database;
  }

  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    return database.save(invoice);
  }

  public void delete(Long id) throws DatabaseOperationException {
    database.delete(id);
  }

  public Collection<Invoice> findAll() throws DatabaseOperationException {
    return database.findAll();
  }

  public Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException {
    return database.findByBuyer(companyId);
  }

  public Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException {
    return database.findBySeller(companyId);
  }

  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return database.findByDate(fromDate, toDate);
  }

  public Invoice findOne(Long id) throws DatabaseOperationException {
    return database.findOne(id);
  }
}
