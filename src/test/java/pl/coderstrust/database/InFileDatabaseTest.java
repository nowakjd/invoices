package pl.coderstrust.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class InFileDatabaseTest {

  private InFileDatabase inFileDatabase;
  private Invoice invoice1;
  private Invoice invoice2;
  private Invoice invoice3;
  private Invoice invoice4;
  private Invoice invoice5;
  private Invoice invoice6;
  private Company seller1;
  private Company seller2;
  private Company buyer1;
  private Company buyer2;

  @BeforeEach
  void setUp() {
    try {
      inFileDatabase = new InFileDatabase("database.txt", "id.txt");
    } catch (DatabaseOperationException exception) {
      exception.printStackTrace();
    }
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
    LocalDate yesterday = LocalDate.now().minusDays(1);
    LocalDate fourDaysEgo = LocalDate.now().minusDays(4);
    LocalDate weekEgo = LocalDate.now().minusDays(7);
    invoice1 = new Invoice(0, weekEgo, invoiceEntries, "FA/111/2018", seller1, buyer1);
    LocalDate today = LocalDate.now();
    invoice2 = new Invoice(0, fourDaysEgo, invoiceEntries, "FA/222/2018", seller1, buyer2);
    invoice3 = new Invoice(1L, yesterday, invoiceEntries, "FA/333/2018", seller2, buyer1);
    invoice4 = new Invoice(2L, today, invoiceEntries, "FA/444/2018", seller2, buyer2);
    invoice5 = new Invoice(666L, today, invoiceEntries, "FA/444/2018", seller2, buyer2);
    invoice6 = new Invoice(0, yesterday, invoiceEntries, "FA/444/2018", seller2, buyer2);
  }

  @AfterEach
  public void cleanUpEach() {
    File database = new File("database.txt");
    File id = new File("id.txt");
    database.delete();
    id.delete();
  }

  @Test
  @DisplayName("Update invoice with existing id")
  void updateInvoice() throws DatabaseOperationException {
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    assertEquals(invoice1.getIssue(), inFileDatabase.findOne(1L).getIssue());
    assertEquals(invoice2.getIssue(), inFileDatabase.findOne(2L).getIssue());
    inFileDatabase.save(invoice3);
    inFileDatabase.save(invoice4);
    assertEquals(invoice3.getIssue(), inFileDatabase.findOne(1L).getIssue());
    assertEquals(invoice4.getIssue(), inFileDatabase.findOne(2L).getIssue());
  }

  @Test
  @DisplayName("Operations with not existing id")
  void operationsWithNotExistingId() {
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.save(invoice5));
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.delete(123L));
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.findOne(123L));
  }


  @Test
  @DisplayName("Find invoices by buyer")
  void findByBuyerTest() throws DatabaseOperationException {
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    inFileDatabase.save(invoice1);
    assertEquals(5, inFileDatabase.findAll().size());
    assertEquals(3, inFileDatabase.findByBuyer(buyer1).size());
    assertEquals(2, inFileDatabase.findByBuyer(buyer2).size());
  }

  @Test
  @DisplayName("Find invoices by seller")
  void findBySellerTest() throws DatabaseOperationException {
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    inFileDatabase.save(invoice1);
    assertEquals(5, inFileDatabase.findAll().size());
    assertEquals(5, inFileDatabase.findBySeller(seller1).size());
    assertEquals(0, inFileDatabase.findBySeller(seller2).size());
  }

  @Test
  @DisplayName("Find invoices by date")
  void findByDateTest() throws DatabaseOperationException {
    inFileDatabase.save(invoice1);
    inFileDatabase.save(invoice2);
    inFileDatabase.save(invoice6);
    assertEquals(3, inFileDatabase.findAll().size());
    Collection<Invoice> actual1 = inFileDatabase
        .findByDate(LocalDate.now().minusDays(4), LocalDate.now());
    Collection<Invoice> actual2 = inFileDatabase
        .findByDate(LocalDate.now().minusDays(10), LocalDate.now().minusDays(6));
    assertEquals(2, actual1.size());
    assertEquals(1, actual2.size());
  }

  @Test
  @DisplayName("Exception for find by null Company")
  void findByNullCompany() {
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.findByBuyer(null));
    assertThrows(DatabaseOperationException.class, () -> inFileDatabase.findBySeller(null));

  }


}
