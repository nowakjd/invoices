package pl.coderstrust.database;


import com.fasterxml.jackson.core.JsonProcessingException;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class InFileDatabase implements Database {

  private String databaseFileName;
  private NewId newId;
  private ArrayList<Invoice> invoices;
  private FileProcessor fileProcessor;
  private JsonConverter jsonConverter;

  public InFileDatabase(String databaseFileName, String idFileName)
      throws DatabaseOperationException {
    this.databaseFileName = databaseFileName;
    newId = new NewId(idFileName);
    fileProcessor = new FileProcessor(databaseFileName);
    jsonConverter = new JsonConverter();
  }

  @Override
  public void save(Invoice invoice) throws DatabaseOperationException {
    final long id = invoice.getId();
    final LocalDate issueDate = invoice.getIssueDate();
    final List<InvoiceEntry> entries = invoice.getEntries();
    final String issue = invoice.getIssue();
    final Company seller = invoice.getSeller();
    final Company buyer = invoice.getBuyer();
    try {
      if (id == 0) {
        fileProcessor.addLine(jsonConverter
            .convert(new Invoice(newId.getNewId(), issueDate, entries, issue, seller, buyer)));

      } else {
        delete(invoice.getId());
        fileProcessor.addLine(jsonConverter.convert(invoice));
      }
    } catch (JsonProcessingException exception) {
      exception.printStackTrace();
      throw new DatabaseOperationException("error during json converting");
    }
  }

  @Override
  public void delete(Long id) throws DatabaseOperationException {
    Invoice toDelete = findOne(id);
    try {
      fileProcessor.removeLine(jsonConverter.convert(toDelete));
    } catch (JsonProcessingException exception) {
      exception.printStackTrace();
      throw new DatabaseOperationException("error during json converting");
    }

  }

  @Override
  public Collection<Invoice> findAll() throws DatabaseOperationException {
    getAll();
    return invoices;
  }

  @Override
  public Collection<Invoice> findByBuyer(Company company) throws DatabaseOperationException {
    getAll();
    return (ArrayList<Invoice>) invoices.stream()
        .filter(invoice -> company.equals(invoice.getBuyer())).collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findBySeller(Company company) throws DatabaseOperationException {
    getAll();
    return invoices.stream()
        .filter(invoice -> company.equals(invoice.getSeller())).collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    getAll();
    return (ArrayList<Invoice>) invoices.stream()
        .filter(invoice -> fromDate.isAfter(invoice.getIssueDate()) && toDate
            .isBefore(invoice.getIssueDate()) || fromDate.isEqual(invoice.getIssueDate())
            || toDate.isEqual(invoice.getIssueDate())).collect(Collectors.toList());
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {
    getAll();
    Invoice result;
    try {
      result = invoices.stream().filter(invoice -> id == invoice.getId()).findFirst().get();
    } catch (NoSuchElementException exception) {
      throw new DatabaseOperationException("Invoice of id: " + id + " does not exist.");
    }
    return result;
  }

  private void getAll() throws DatabaseOperationException {
    try {
      invoices = jsonConverter.convert(fileProcessor.getLines());
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new DatabaseOperationException("IOException");
    }
  }

}
