package pl.coderstrust.database;

import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public class InMemoryDatabase implements Database {

  @Override
  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    return null;
  }

  @Override
  public void delete(Long id) throws DatabaseOperationException {

  }

  @Override
  public Collection<Invoice> findAll() throws DatabaseOperationException {
    return null;
  }

  @Override
  public Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException {
    return null;
  }

  @Override
  public Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException {
    return null;
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return null;
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {
    return null;
  }
}
