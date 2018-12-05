package pl.coderstrust.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(Lifecycle.PER_CLASS)
class InMemoryDatabaseTest {

  private Invoice invoice1;
  private Invoice invoice2;
  private Invoice invoice3;
  private Invoice invoice4;
  private Invoice invoice5;
  private Invoice invoice6;
  private Invoice invoice7;
  private Company seller1;
  private Company buyer1;

  @BeforeAll
  void setUp() {
    Address address = new Address("Wall Street", "12/55B", "New York", "12-999");
    seller1 = new Company(1L, "Microsoft", address, "5272830422",
        "11114015601081110181488249");
    Company buyer2 = new Company(43L, "Apple", address, "6570011469", "11114015601081110181488249");
    buyer1 = new Company(41L, "Netflix", address, "6570011469", "11114015601081110181488249");
    InvoiceEntry invoiceEntry1 = new InvoiceEntry(10, "Lego", "piece", BigDecimal.valueOf(199.99),
        Vat.RATE_23,
        BigDecimal.valueOf(199.99), BigDecimal.valueOf(500));
    Company seller2 = new Company(2L, "Bush", address, "5272830422", "11114015601081110181488249");
    InvoiceEntry invoiceEntry2 = new InvoiceEntry(4, "Barbie", "piece", BigDecimal.valueOf(123.11),
        Vat.RATE_8,
        BigDecimal.valueOf(399.99), BigDecimal.valueOf(100));
    InvoiceEntry invoiceEntry3 = new InvoiceEntry(1, "Sand", "kg",
        BigDecimal.valueOf(19), Vat.RATE_0, BigDecimal.valueOf(99), BigDecimal.valueOf(12));
    List<InvoiceEntry> invoiceEntries = new ArrayList<>(
        Arrays.asList(invoiceEntry1, invoiceEntry2, invoiceEntry3));
    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate fourDaysAgo = LocalDate.now().minusDays(4);
    LocalDate weekAgo = LocalDate.now().minusDays(7);
    invoice1 = new Invoice(null, weekAgo, invoiceEntries, "FA/111/2018", seller1, buyer1);
    LocalDate today = LocalDate.now();
    invoice2 = new Invoice(null, fourDaysAgo, invoiceEntries, "FA/222/2018", seller1, buyer2);
    invoice3 = new Invoice(1L, yesterday, invoiceEntries, "FA/333/2018", seller2, buyer1);
    invoice4 = new Invoice(2L, today, invoiceEntries, "FA/444/2018", seller2, buyer2);
    invoice5 = new Invoice(666L, today, invoiceEntries, "FA/444/2018", seller2, buyer2);
    invoice6 = new Invoice(null, yesterday, invoiceEntries, "FA/444/2018", seller2, buyer2);
    invoice7 = new Invoice(2L, fourDaysAgo, invoiceEntries, "FA/98/2015", seller1, buyer1);
  }

  @Test
  @DisplayName("Saving new invoices")
  void saveNewInvoice() throws DatabaseOperationException {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //when
    Invoice persistentInvoice1 = inMemoryDatabase.save(invoice1);

    //then
    assertEquals(persistentInvoice1, data.get(persistentInvoice1.getId()));
    assertEquals(invoice1.getIssue(), persistentInvoice1.getIssue());
    assertEquals(invoice1.getIssueDate(), persistentInvoice1.getIssueDate());
    assertEquals(invoice1.getBuyer(), persistentInvoice1.getBuyer());
    assertEquals(invoice1.getIssue(), persistentInvoice1.getIssue());
    assertEquals(invoice1.getSeller(), persistentInvoice1.getSeller());
    assertEquals(invoice1.getEntries(), persistentInvoice1.getEntries());
  }

  @Test
  @DisplayName("Update invoice with existing id")
  void updateInvoice() throws DatabaseOperationException {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    data.put(2L, invoice4);
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //when
    Invoice persistentInvoice = inMemoryDatabase.save(invoice7);

    //then
    assertEquals(invoice7, data.get(persistentInvoice.getId()));
  }

  @Test
  @DisplayName("Operations with not existing id and null values")
  void operationsWithNotExistingId() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //then
    assertThrows(DatabaseOperationException.class, () -> inMemoryDatabase.save(invoice5));
    assertThrows(IllegalArgumentException.class, () -> inMemoryDatabase.save(null));
    assertThrows(DatabaseOperationException.class, () -> inMemoryDatabase.delete(123L));
    assertThrows(DatabaseOperationException.class, () -> inMemoryDatabase.delete(null));
    assertThrows(DatabaseOperationException.class, () -> inMemoryDatabase.findOne(123L));
    assertThrows(DatabaseOperationException.class, () -> inMemoryDatabase.findOne(null));
  }

  @Test
  @DisplayName("Delete invoice")
  void deleteInvoice() throws DatabaseOperationException {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    data.put(1L, invoice3);
    data.put(2L, invoice4);
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //when
    inMemoryDatabase.delete(invoice3.getId());

    //then
    assertFalse(data.containsValue(invoice3));
    assertTrue(data.containsValue(invoice4));
  }

  @Test
  @DisplayName("Find invoices by buyer")
  void findByBuyerTest() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);
    data.put(1L, invoice1);
    data.put(2L, invoice2);
    data.put(3L, invoice3);
    data.put(4L, invoice4);
    data.put(5L, invoice5);
    data.put(6L, invoice6);
    data.put(7L, invoice7);

    //when
    List<Invoice> listOfBuyer = (List<Invoice>) inMemoryDatabase.findByBuyer(buyer1.getCompanyId());

    //then
    assertEquals(3, listOfBuyer.size());
    for (Invoice invoice : listOfBuyer) {
      assertEquals(invoice.getBuyer(), buyer1);
    }
  }

  @Test
  @DisplayName("Find invoices by seller")
  void findBySellerTest() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);
    data.put(1L, invoice1);
    data.put(2L, invoice2);
    data.put(3L, invoice3);
    data.put(4L, invoice4);
    data.put(5L, invoice5);
    data.put(6L, invoice6);
    data.put(7L, invoice7);

    //when
    List<Invoice> listOfSeller = (List<Invoice>) inMemoryDatabase
        .findBySeller(seller1.getCompanyId());

    //then
    assertEquals(3, listOfSeller.size());
    for (Invoice invoice : listOfSeller) {
      assertEquals(invoice.getSeller(), seller1);
    }
  }

  @Test
  @DisplayName("Find invoices by date at yesterday to now")
  void findByDateTestAtYesterdayToNow() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);
    data.put(1L, invoice1); //weekAgo
    data.put(2L, invoice2); //fourDaysAgo
    data.put(3L, invoice3); //yesterday
    data.put(4L, invoice4); //today
    data.put(5L, invoice5); //today
    data.put(6L, invoice6); //yesterday
    data.put(7L, invoice7); //fourDaysAgo
    LocalDate yesterday = LocalDate.now().minusDays(1);

    //when
    List<Invoice> atYesterdayToNow = (List<Invoice>) inMemoryDatabase
        .findByDate(yesterday, LocalDate.now());

    //then
    assertEquals(4, atYesterdayToNow.size());
  }

  @Test
  @DisplayName("Find invoices by date at week ago to yesterday")
  void findByDateTestAtWeekAgoToYesterday() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);
    data.put(1L, invoice1); //weekAgo
    data.put(2L, invoice2); //fourDaysAgo
    data.put(3L, invoice3); //yesterday
    data.put(4L, invoice4); //today
    data.put(5L, invoice5); //today
    data.put(6L, invoice6); //yesterday
    data.put(7L, invoice7); //fourDaysAgo
    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate weekAgo = LocalDate.now().minusDays(7);

    //when
    List<Invoice> atWeekAgoToYesterday = (List<Invoice>) inMemoryDatabase
        .findByDate(weekAgo, yesterday);

    //then
    assertEquals(5, atWeekAgoToYesterday.size());
  }

  @Test
  @DisplayName("Find invoices by date  at week ago to now")
  void findByDateTestAtWeekAgoToNow() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);
    data.put(1L, invoice1); //weekAgo
    data.put(2L, invoice2); //fourDaysAgo
    data.put(3L, invoice3); //yesterday
    data.put(4L, invoice4); //today
    data.put(5L, invoice5); //today
    data.put(6L, invoice6); //yesterday
    data.put(7L, invoice7); //fourDaysAgo
    LocalDate weekAgo = LocalDate.now().minusDays(7);

    //when
    List<Invoice> atWeekAgoToNow = (List<Invoice>) inMemoryDatabase
        .findByDate(weekAgo, LocalDate.now());

    //then
    assertEquals(7, atWeekAgoToNow.size());
  }

  @Test
  @DisplayName("Find all invoices")
  void findAllTest() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    data.put(1L, invoice1);
    data.put(2L, invoice2);
    data.put(3L, invoice3);
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //when
    int actual = inMemoryDatabase.findAll().size();

    //then
    assertEquals(3, actual);
  }

  @Test
  @DisplayName("Exception for find by null Company")
  void findByNullCompany() {
    //given
    Map<Long, Invoice> data = new HashMap<>();
    InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(data);

    //then
    assertThrows(IllegalArgumentException.class, () -> inMemoryDatabase.findByBuyer(null));
    assertThrows(IllegalArgumentException.class, () -> inMemoryDatabase.findBySeller(null));
  }
}
