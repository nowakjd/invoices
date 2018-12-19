package pl.coderstrust.database.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.database.Database;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "hibernateDatabase")
@Repository
public class HibernateDatabase implements Database {

  private InvoiceRepository invoiceRepository;
  private CompanyRepository companyRepository;

  @Autowired
  public HibernateDatabase(InvoiceRepository invoiceRepository,
      CompanyRepository companyRepository) {
    this.invoiceRepository = invoiceRepository;
    this.companyRepository = companyRepository;
  }

  @Override
  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    return invoiceRepository.save(invoice);
  }

  @Override
  public void delete(Long id) throws DatabaseOperationException {
    invoiceRepository.deleteById(id);
  }

  @Override
  public Collection<Invoice> findAll() throws DatabaseOperationException {
    return invoiceRepository.findAll();
  }

  @Override
  public Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException {
    Company company = companyRepository.findById(companyId).get();
    return invoiceRepository.findByBuyer(company);
  }

  @Override
  public Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException {
    Company company = companyRepository.findById(companyId).get();
    return invoiceRepository.findBySeller(company);
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return invoiceRepository.findAll().stream()
        .filter(invoice -> !fromDate.isAfter(invoice.getIssueDate()) && !toDate
            .isBefore(invoice.getIssueDate()))
        .collect(Collectors.toList());
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {
    return invoiceRepository.findById(id).get();
  }
}
