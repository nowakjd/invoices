package pl.coderstrust.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class InvoiceTest {

  private static final String NUMBER = "FV 2018/01/12345";
  private static final LocalDate ISSUE_DATE = LocalDate.of(2018, 11, 1);
  private static final Company SELLER = null;
  private static final Company BUYER = null;
  private static final List<InvoiceEntry> ENTRIES = null;

  @Test
  void testSettersForCorrectArguments() {
    Invoice invoice = new Invoice();
    invoice.setNumber(NUMBER);
    invoice.setIssueDate(ISSUE_DATE);
    invoice.setSeller(SELLER);
    invoice.setBuyer(BUYER);
    invoice.setEntries(ENTRIES);

    assertAll(
        () -> assertEquals(NUMBER, invoice.getNumber()),
        () -> assertEquals(ISSUE_DATE, invoice.getIssueDate()),
        () -> assertEquals(SELLER, invoice.getSeller()),
        () -> assertEquals(BUYER, invoice.getBuyer()),
        () -> assertIterableEquals(ENTRIES, invoice.getEntries()));
  }

  @Test
  void testGetters() {
    Invoice invoice = new Invoice(NUMBER, ISSUE_DATE, SELLER, BUYER, ENTRIES);

    assertAll(
        () -> assertEquals(NUMBER, invoice.getNumber()),
        () -> assertEquals(ISSUE_DATE, invoice.getIssueDate()),
        () -> assertEquals(SELLER, invoice.getSeller()),
        () -> assertEquals(BUYER, invoice.getBuyer()),
        () -> assertIterableEquals(ENTRIES, invoice.getEntries()));
  }
}
