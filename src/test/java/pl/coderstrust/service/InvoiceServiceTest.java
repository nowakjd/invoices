package pl.coderstrust.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.ArrayList;
import java.util.Collection;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

  private InvoiceService invoiceService;
  private Invoice invoice;
  private Collection<Invoice> invoiceCollection;
  private Company company;
  private LocalDate fromDate;
  private LocalDate toDate;

  @Mock
  private Database database;

  @BeforeEach
  void setUp() {
    invoiceService = new InvoiceService(database);
    invoice = new Invoice(1, LocalDate.of(2018, 10, 1),
        null, "2018/01/1234567", null, null);
    invoiceCollection = new ArrayList<>();
    company = new Company(1,"Kogucik",null,
        "6570011469","54114020040000340277983541");
    fromDate = LocalDate.now().minusDays(7);
    toDate = LocalDate.now();
  }

  @Test
  @DisplayName("Checking the save method call")
  void saveMethodTest() throws DatabaseOperationException {
    when(database.save(invoice)).thenReturn(invoice);
    invoiceService.save(invoice);
    verify(database).save(any(Invoice.class));
  }

  @Test
  @DisplayName("Checking the delete method call")
  void deleteMethodTest() throws DatabaseOperationException {
    doNothing().when(database).delete(1L);
    invoiceService.delete(1L);
    verify(database).delete(any());
  }

  @Test
  @DisplayName("Checking the findAll method call")
  void findAllMethodTest() throws DatabaseOperationException {
    when(database.findAll()).thenReturn(invoiceCollection);
    invoiceService.findAll();
    verify(database).findAll();
  }

  @Test
  @DisplayName("Checking the findByBuyer method call")
  void findByBuyerMethodTest() throws DatabaseOperationException {
    when(database.findByBuyer(company)).thenReturn(invoiceCollection);
    invoiceService.findByBuyer(company);
    verify(database).findByBuyer(company);
  }

  @Test
  @DisplayName("Checking the findBySeller method call")
  void findBySellerMethodTest() throws DatabaseOperationException {
    when(database.findBySeller(company)).thenReturn(invoiceCollection);
    invoiceService.findBySeller(company);
    verify(database).findBySeller(company);
  }

  @Test
  @DisplayName("Checking the findByDate method call")
  void findByDateMethodTest() throws DatabaseOperationException {
    when(database.findByDate(fromDate, toDate)).thenReturn(invoiceCollection);
    invoiceService.findByDate(fromDate, toDate);
    verify(database).findByDate(fromDate, toDate);
  }

  @Test
  @DisplayName("Checking the findOne method call")
  void findOneMethodTest() throws DatabaseOperationException {
    when(database.findOne(1L)).thenReturn(invoice);
    invoiceService.findOne(1L);
    verify(database).findOne(1L);
  }
}