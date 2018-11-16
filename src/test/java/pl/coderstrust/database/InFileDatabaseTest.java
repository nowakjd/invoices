package pl.coderstrust.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.only;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.database.file.FileProcessor;
import pl.coderstrust.database.file.IdGenerator;
import pl.coderstrust.database.file.JsonConverter;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class InFileDatabaseTest {

  private static Invoice invoice1;
  private static Invoice invoice2;
  private static Invoice invoice3;
  private static Invoice invoice4;
  private static Invoice invoice5;
  private static Company seller1;
  private static Company seller2;
  private static Company buyer1;
  private static Company buyer2;
  private static final ArrayList<String> jsonsListMock = new ArrayList<>(
      Arrays.asList("jsonsListMock"));
  private static ArrayList<Invoice> allInvoices;

  @Mock
  static FileProcessor fileProcessorMock;

  @Mock
  static IdGenerator idGeneratorMock;

  @Mock
  static JsonConverter jsonConverterMock;

  @InjectMocks
  InFileDatabase inFileDatabase = new InFileDatabase(fileProcessorMock, idGeneratorMock,
      jsonConverterMock);

  @BeforeAll
  static void setUp() {
    Address address = new Address("Wall Street", "12/55B", "New York", "12-999");
    seller1 = new Company(1, "Microsoft", address, "5272830422",
        "11114015601081110181488249");
    seller2 = new Company(2, "Bush", address, "5272830422", "11114015601081110181488249");
    buyer1 = new Company(41, "Netflix", address, "6570011469", "11114015601081110181488249");
    buyer2 = new Company(43, "Apple", address, "6570011469", "11114015601081110181488249");
    InvoiceEntry invoiceEntry1 = new InvoiceEntry(10, "Lego", "piece", new BigDecimal(199.99),
        Vat.RATE_23,
        new BigDecimal(199.99), new BigDecimal(500));
    InvoiceEntry invoiceEntry2 = new InvoiceEntry(4, "Barbie", "piece", new BigDecimal(123.11),
        Vat.RATE_8,
        new BigDecimal(399.99), new BigDecimal(100));
    InvoiceEntry invoiceEntry3 = new InvoiceEntry(1, "Sand", "kg", new BigDecimal(19), Vat.RATE_0,
        new BigDecimal(99), new BigDecimal(12));
    List<InvoiceEntry> invoiceEntries = new ArrayList<>(
        Arrays.asList(invoiceEntry1, invoiceEntry2, invoiceEntry3));
    LocalDate weekAgo = LocalDate.now().minusDays(7);
    invoice1 = new Invoice(null, weekAgo, invoiceEntries, "FA/111/2018", seller1, buyer1);
    invoice2 = new Invoice(5L, weekAgo, invoiceEntries, "FA/111/2018", seller1, buyer1);
    LocalDate fourDaysAgo = LocalDate.now().minusDays(4);
    invoice3 = new Invoice(1L, fourDaysAgo, invoiceEntries, "FA/333/2018", seller2, buyer1);
    LocalDate yesterday = LocalDate.now().minusDays(1);
    invoice4 = new Invoice(2L, weekAgo, invoiceEntries, "FA/444/2018", seller2, buyer2);
    LocalDate today = LocalDate.now();
    invoice5 = new Invoice(128L, yesterday, invoiceEntries, "FA/444/2018", seller2, buyer2);
    allInvoices
        = new ArrayList<>(Arrays.asList(invoice2, invoice3, invoice4));
  }

  @Test
  @DisplayName("Save new invoice")
  void saveInvoice() throws DatabaseOperationException, IOException {
    when(idGeneratorMock.getNewId()).thenReturn(5L);
    assertEquals(invoice2, inFileDatabase.save(invoice1));
    verify(jsonConverterMock, only()).convert(ArgumentMatchers.refEq(invoice2));
    verify(fileProcessorMock, never()).removeLine(any());
    verify(fileProcessorMock, only()).addLine(any());
  }

  @Test
  @DisplayName("Update invoice")
  void updateInvoice() throws DatabaseOperationException, IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    inFileDatabase.save(invoice2);
    verify(jsonConverterMock, times(2)).convert(ArgumentMatchers.refEq(invoice2));
    verify(fileProcessorMock, times(1)).removeLine(any());
    verify(fileProcessorMock, times(1)).addLine(any());
  }

  @Test
  @DisplayName("Find all invoices")
  void findAllTest() throws DatabaseOperationException, IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    Collection<Invoice> all = inFileDatabase.findAll();
    assertEquals(3, all.size());
    verify(fileProcessorMock, never()).addLine(any());
    verify(fileProcessorMock, never()).removeLine(any());
  }

  @Test
  @DisplayName("Find invoices by buyer")
  void findByBuyerTest() throws DatabaseOperationException, IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    ArrayList<Invoice> byBuyer2 = (ArrayList<Invoice>) inFileDatabase.findByBuyer(buyer2);
    assertEquals(1, byBuyer2.size());
    assertTrue(
        byBuyer2.contains(invoice4));
    verify(fileProcessorMock, never()).addLine(any());
    verify(fileProcessorMock, never()).removeLine(any());
  }

  @Test
  @DisplayName("Find invoices by seller")
  void findBySellerTest() throws DatabaseOperationException, IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    ArrayList<Invoice> bySeller2 = (ArrayList<Invoice>) inFileDatabase.findBySeller(seller2);
    assertEquals(2, bySeller2.size());
    assertTrue(
        bySeller2.contains(invoice3) && bySeller2.contains(invoice4));
    verify(fileProcessorMock, never()).addLine(any());
    verify(fileProcessorMock, never()).removeLine(any());
  }

  @Test
  @DisplayName("Find invoices by date")
  void findByDateTest() throws DatabaseOperationException, IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    Collection<Invoice> actual1 = inFileDatabase
        .findByDate(LocalDate.now().minusDays(4), LocalDate.now());
    Collection<Invoice> actual2 = inFileDatabase
        .findByDate(LocalDate.now().minusDays(10), LocalDate.now().minusDays(6));
    assertEquals(1, actual1.size());
    assertEquals(2, actual2.size());
    verify(fileProcessorMock, never()).addLine(any());
    verify(fileProcessorMock, never()).removeLine(any());
  }

  @Test
  @DisplayName("Operations with not existing id")
  void operationsWithNotExistingId() throws IOException {
    when(fileProcessorMock.getLines()).thenReturn(jsonsListMock);
    when(jsonConverterMock.convert(jsonsListMock)).thenReturn(allInvoices);
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.save(invoice5));
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.delete(123L));
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.findOne(123L));
  }
}
