package pl.coderstrust.database;

import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

public interface Database {

  Invoice save(Invoice invoice) throws DatabaseOperationException;

  void delete(Long id) throws DatabaseOperationException;

  Collection<Invoice> findAll() throws DatabaseOperationException;

  Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException;

  Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException;

  Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException;

  Invoice findOne(Long id) throws DatabaseOperationException;
}
