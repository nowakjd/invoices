package pl.coderstrust.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.database.Database;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

  private InvoiceService invoiceService;
  private Company company;
  private LocalDate fromDate;
  private LocalDate toDate;

  @Mock
  private Database database;

  @Mock
  private Invoice invoice;

  @BeforeEach
  void setUp() {
    invoiceService = new InvoiceService(database);
    company = new Company(1, "Kogucik", null,
        "6570011469", "54114020040000340277983541");
    fromDate = LocalDate.now().minusDays(7);
    toDate = LocalDate.now();
  }

  @Test
  @DisplayName("Checking the save method call")
  void saveMethodTest() throws DatabaseOperationException {
    invoiceService.save(invoice);
    verify(database).save(invoice);
  }

  @Test
  @DisplayName("Checking the delete method call")
  void deleteMethodTest() throws DatabaseOperationException {
    invoiceService.delete(1L);
    verify(database).delete(1L);
  }

  @Test
  @DisplayName("Checking the findAll method call")
  void findAllMethodTest() throws DatabaseOperationException {
    invoiceService.findAll();
    verify(database).findAll();
  }

  @Test
  @DisplayName("Checking the findByBuyer method call")
  void findByBuyerMethodTest() throws DatabaseOperationException {
    invoiceService.findByBuyer(company);
    verify(database).findByBuyer(company);
  }

  @Test
  @DisplayName("Checking the findBySeller method call")
  void findBySellerMethodTest() throws DatabaseOperationException {
    invoiceService.findBySeller(company);
    verify(database).findBySeller(company);
  }

  @Test
  @DisplayName("Checking the findByDate method call")
  void findByDateMethodTest() throws DatabaseOperationException {
    invoiceService.findByDate(fromDate, toDate);
    verify(database).findByDate(fromDate, toDate);
  }

  @Test
  @DisplayName("Checking the findOne method call")
  void findOneMethodTest() throws DatabaseOperationException {
    invoiceService.findOne(1L);
    verify(database).findOne(1L);
  }
}
