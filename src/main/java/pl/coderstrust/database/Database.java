package pl.coderstrust.database;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

public interface Database {

  void save(Invoice invoice) throws DatabaseOperationException;

  void deleteById(Long id) throws DatabaseOperationException;

  Collection<Invoice> findAll() throws DatabaseOperationException;

  Collection<Invoice> findByBuyer(Company company) throws DatabaseOperationException;

  Collection<Invoice> findBySeller(Company company) throws DatabaseOperationException;

  Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate) throws DatabaseOperationException;

  Invoice findById(Long id) throws DatabaseOperationException;
}
