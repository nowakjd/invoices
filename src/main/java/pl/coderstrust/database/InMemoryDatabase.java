package pl.coderstrust.database;

import org.springframework.stereotype.Repository;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class InMemoryDatabase implements Database {

  private Map<Long, Invoice> invoicesStorage;
  private Long newId = 1L;

  public InMemoryDatabase() {
  }

  InMemoryDatabase(Map<Long, Invoice> invoicesStorage) {
    this.invoicesStorage = invoicesStorage;
  }

  @Override
  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Failed to save. Invoice should not be null");
    }
    Invoice result;
    if (invoice.getId() == null) {
      result = insert(invoice);
    } else {
      result = update(invoice);
    }
    return result;
  }

  private Invoice update(Invoice invoice) throws DatabaseOperationException {
    Invoice result;
    if (invoicesStorage.put(invoice.getId(), invoice) == null) {
      throw new DatabaseOperationException("Failed to update. Invoice of id: "
          + invoice.getId() + " does not exist.");
    }
    result = invoice;
    return result;
  }

  private Invoice insert(Invoice invoice) {
    Invoice result;
    final LocalDate issueDate = invoice.getIssueDate();
    final List<InvoiceEntry> entries = invoice.getEntries();
    final String issue = invoice.getIssue();
    final Company seller = invoice.getSeller();
    final Company buyer = invoice.getBuyer();
    result = new Invoice(newId, issueDate, entries, issue, seller, buyer);
    invoicesStorage.put(newId, result);
    newId++;
    return result;
  }

  @Override
  public void delete(Long id) throws DatabaseOperationException {
    if (invoicesStorage.remove(id) == null) {
      throw new DatabaseOperationException("Invoice of id: " + id + " does not exist.");
    }
  }

  @Override
  public Collection<Invoice> findAll() {
    return new ArrayList<>(invoicesStorage.values());
  }

  @Override
  public Collection<Invoice> findByBuyer(Long companyId) {
    if (companyId == null) {
      throw new IllegalArgumentException("Buyer ID should not be null");
    }
    return invoicesStorage.values().stream()
        .filter(invoice -> invoice.getBuyer().getCompanyId().equals(companyId))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findBySeller(Long companyId) {
    if (companyId == null) {
      throw new IllegalArgumentException("Seller ID should not be null");
    }
    return invoicesStorage.values().stream()
        .filter(invoice -> invoice.getSeller().getCompanyId().equals(companyId))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate) {
    return invoicesStorage.values().stream()
        .filter(invoice -> (fromDate.isBefore(invoice.getIssueDate())
            && toDate.isAfter(invoice.getIssueDate())) || fromDate.isEqual(invoice.getIssueDate())
            || toDate.isEqual(invoice.getIssueDate()))
        .collect(Collectors.toList());
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {
    final Invoice invoice = invoicesStorage.get(id);
    if (invoice == null) {
      throw new DatabaseOperationException("Invoice of id: " + id + " does not exist.");
    }
    return invoice;
  }
}
