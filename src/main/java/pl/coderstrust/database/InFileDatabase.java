package pl.coderstrust.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.coderstrust.database.infiledatabase.FileProcessor;
import pl.coderstrust.database.infiledatabase.IdGenerator;
import pl.coderstrust.database.infiledatabase.JsonConverter;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class InFileDatabase implements Database {

  private IdGenerator idGenerator;
  private FileProcessor fileProcessor;
  private JsonConverter jsonConverter;

  public InFileDatabase(FileProcessor fileProcessor , IdGenerator idGenerator, JsonConverter jsonConverter) {
    this.fileProcessor = fileProcessor;
    this.idGenerator= idGenerator;
    this.jsonConverter=jsonConverter;
  }

  public InFileDatabase(String databaseFileName, String idFileName)
      throws DatabaseOperationException {
    idGenerator = new IdGenerator(idFileName);
    fileProcessor = new FileProcessor(databaseFileName);
    jsonConverter = new JsonConverter();
  }

  @Override
  public void save(Invoice invoice) throws DatabaseOperationException {
    final long id = invoice.getId();
    try {
      if (id == 0) {
        fileProcessor.addLine(jsonConverter
            .convert(
                new Invoice(idGenerator.getNewId(), invoice.getIssueDate(), invoice.getEntries(),
                    invoice.getIssue(), invoice.getSeller(), invoice.getBuyer())));
      } else {
        delete(id);
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
    return getAll();
  }

  @Override
  public Collection<Invoice> findByBuyer(Company company) throws DatabaseOperationException {
    return getAll().stream()
        .filter(invoice -> company.equals(invoice.getBuyer())).collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findBySeller(Company company) throws DatabaseOperationException {

    return getAll().stream()
        .filter(invoice -> company.equals(invoice.getSeller())).collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return getAll().stream()
        .filter(invoice -> !fromDate.isAfter(invoice.getIssueDate()) && !toDate
            .isBefore(invoice.getIssueDate())).collect(Collectors.toList());
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {

    Invoice result;
    try {
      result = getAll().stream().filter(invoice -> id == invoice.getId()).findFirst().get();
    } catch (NoSuchElementException exception) {
      throw new DatabaseOperationException("Invoice of id: " + id + " does not exist.");
    }
    return result;
  }

  private ArrayList<Invoice> getAll() throws DatabaseOperationException {
    try {
      return jsonConverter.convert(fileProcessor.getLines());
    } catch (IOException exception) {
      exception.printStackTrace();
      throw new DatabaseOperationException("IOException");
    }
  }

}
