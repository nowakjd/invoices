package pl.coderstrust.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

class InvoiceTest {

  private static final long ID = 1;
  private static final String ISSUE = "2018/01/1234567";
  private static final LocalDate ISSUE_DATE = LocalDate.of(2018, 10, 1);
  private static final Company SELLER = null;
  private static final Company BUYER = null;
  private static final List<InvoiceEntry> ENTRIES = null;

  @Test
  @DisplayName("Checking getters")
  void testGetters() {
    Invoice invoice = new Invoice(ID, ISSUE, ISSUE_DATE, SELLER, BUYER, ENTRIES);

    assertAll(
        () -> assertEquals(ID, invoice.getId()),
        () -> assertEquals(ISSUE, invoice.getIssue()),
        () -> assertEquals(ISSUE_DATE, invoice.getIssueDate()),
        () -> assertEquals(SELLER, invoice.getSeller()),
        () -> assertEquals(BUYER, invoice.getBuyer()),
        () -> assertIterableEquals(ENTRIES, invoice.getEntries()));
  }

  @ParameterizedTest
  @DisplayName("Test invalid values of id")
  @ValueSource(longs = {-1, 0})
  void invaldValueOfIdTest(long id) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> {
          Invoice invoice = new Invoice(id, ISSUE, ISSUE_DATE, SELLER, BUYER, ENTRIES);
        });
    assertEquals("The id should be greater than zero", exception.getMessage());
  }

  @Test
  @DisplayName("Checking date out of range")
  void testInvalidDate() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> {
          Invoice invoice = new Invoice(ID, ISSUE, LocalDate.of(2018, 12, 10), SELLER, BUYER,
              ENTRIES);
        });
    assertEquals("Passing date cannot be in the future", exception.getMessage());
  }
}
