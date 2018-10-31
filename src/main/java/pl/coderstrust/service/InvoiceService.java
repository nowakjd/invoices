package pl.coderstrust.service;

import java.time.LocalDate;
import java.util.Collection;
import pl.coderstrust.database.Database;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

public class InvoiceService {

  private Database database;

  public InvoiceService(Database database) {
    this.database = database;
  }

  public void save(Invoice invoice) throws DatabaseOperationException {
    database.save(invoice);
  }

  public void delete(Long id) throws DatabaseOperationException {
    database.delete(id);
  }

  Collection<Invoice> findAll() throws DatabaseOperationException {
    return database.findAll();
  }

  Collection<Invoice> findByBuyer(Company company) throws DatabaseOperationException {
    return database.findByBuyer(company);
  }

  Collection<Invoice> findBySeller(Company company) throws DatabaseOperationException {
    return database.findBySeller(company);
  }

  Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return database.findByDate(fromDate, toDate);
  }

  Invoice findOne(Long id) throws DatabaseOperationException {
    return database.findOne(id);
  }
}
