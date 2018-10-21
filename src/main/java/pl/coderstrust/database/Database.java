package pl.coderstrust.database;

import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

public interface Database {

  void save(Invoice invoice);

  void update(Invoice invoice);

  void delete(long id);

  Collection<Invoice> findAll();

  Collection<Invoice> findByBuyer(Company company);

  Collection<Invoice> findBySeller(Company company);

  Collection<Invoice> find(LocalDate fromDate, LocalDate toDate);


}
