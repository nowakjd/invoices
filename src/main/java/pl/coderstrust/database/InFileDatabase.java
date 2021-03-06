package pl.coderstrust.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import pl.coderstrust.database.file.FileProcessor;
import pl.coderstrust.database.file.IdGenerator;
import pl.coderstrust.database.file.JsonConverter;
import pl.coderstrust.model.Invoice;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "pl.coderstrust.database", havingValue = "file")
@Repository
public class InFileDatabase implements Database {

  private final IdGenerator idGenerator;
  private final FileProcessor fileProcessor;
  private final JsonConverter jsonConverter;

  InFileDatabase(FileProcessor fileProcessor, IdGenerator idGenerator,
      JsonConverter jsonConverter) {
    this.fileProcessor = fileProcessor;
    this.idGenerator = idGenerator;
    this.jsonConverter = jsonConverter;
  }

  @Override
  public Invoice save(Invoice invoice) throws DatabaseOperationException {
    if (invoice == null) {
      throw new IllegalArgumentException("Failed to save. Invoice should not be null");
    }
    final Long id = invoice.getId();
    try {
      if (id == null) {
        invoice = new Invoice(idGenerator.getNewId(), invoice.getIssueDate(), invoice.getEntries(),
            invoice.getIssue(), invoice.getSeller(), invoice.getBuyer());
        fileProcessor.addLine(jsonConverter
            .convert(invoice));
      } else {
        delete(id);
        fileProcessor.addLine(jsonConverter.convert(invoice));
      }
    } catch (JsonProcessingException exception) {
      throw new DatabaseOperationException("Error during json converting", exception);
    }
    return invoice;
  }

  @Override
  public void delete(Long id) throws DatabaseOperationException {
    Invoice toDelete = findOne(id);
    try {
      fileProcessor.removeLine(jsonConverter.convert(toDelete));
    } catch (JsonProcessingException exception) {
      throw new DatabaseOperationException("Error during json converting", exception);
    }
  }

  @Override
  public Collection<Invoice> findAll() throws DatabaseOperationException {
    try {
      return jsonConverter.convert(fileProcessor.getLines());
    } catch (IOException exception) {
      throw new DatabaseOperationException("IOException", exception);
    }
  }

  @Override
  public Collection<Invoice> findByBuyer(Long companyId) throws DatabaseOperationException {
    return findAll().stream()
        .filter(invoice -> companyId.equals(invoice.getBuyer().getCompanyId()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findBySeller(Long companyId) throws DatabaseOperationException {
    return findAll().stream()
        .filter(invoice -> companyId.equals(invoice.getSeller().getCompanyId()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<Invoice> findByDate(LocalDate fromDate, LocalDate toDate)
      throws DatabaseOperationException {
    return findAll().stream()
        .filter(invoice -> !fromDate.isAfter(invoice.getIssueDate()) && !toDate
            .isBefore(invoice.getIssueDate())).collect(Collectors.toList());
  }

  @Override
  public Invoice findOne(Long id) throws DatabaseOperationException {
    Invoice result;
    result = findAll().stream()
        .filter(invoice -> id.equals(invoice.getId())).findFirst().orElse(null);
    if (result == null) {
      throw new DatabaseOperationException("Invoice of id: " + id + " does not exist.");
    }
    return result;
  }
}
