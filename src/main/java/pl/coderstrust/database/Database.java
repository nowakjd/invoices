package pl.coderstrust.database;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

public interface Database {

  void save(Invoice invoice) throws DatabaseOperationException;

  void update(Invoice invoice) throws DatabaseOperationException;

  void delete(long id) throws DatabaseOperationException;

  Collection<Invoice> findAll() throws DatabaseOperationException;

  Collection<Invoice> findByBuyer(Company company) throws DatabaseOperationException;

  Collection<Invoice> findBySeller(Company company) throws DatabaseOperationException;

  Collection<Invoice> find(LocalDate fromDate, LocalDate toDate) throws DatabaseOperationException;


}
