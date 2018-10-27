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
import java.util.ArrayList;
import java.util.List;

class InvoiceTest {

  private static final Long ID = 1L;
  private static final LocalDate ISSUE_DATE = LocalDate.of(2018, 10, 1);
  private static final List<InvoiceEntry> ENTRIES = null;
  private static final String ISSUE = "2018/01/1234567";
  private static final Company SELLER = null;
  private static final Company BUYER = null;

  @Test
  @DisplayName("Checking getters")
  void testGetters() {
    Invoice invoice = new Invoice(ID, ISSUE_DATE, ENTRIES, ISSUE, SELLER, BUYER);

    assertAll(
        () -> assertEquals(ID, invoice.getId()),
        () -> assertEquals(ISSUE, invoice.getIssue()),
        () -> assertIterableEquals(new ArrayList<>(), invoice.getEntries()),
        () -> assertEquals(ISSUE_DATE, invoice.getIssueDate()),
        () -> assertEquals(SELLER, invoice.getSeller()),
        () -> assertEquals(BUYER, invoice.getBuyer()));
  }

  @ParameterizedTest
  @DisplayName("Test invalid values of id")
  @ValueSource(longs = {-2, -1})
  void invalidValueOfIdTest(long id) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Invoice(id, ISSUE_DATE, ENTRIES, ISSUE, SELLER, BUYER));
    assertEquals("The id should be greater than zero", exception.getMessage());
  }

  @Test
  @DisplayName("Checking date out of range")
  void testInvalidDate() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> new Invoice(ID, LocalDate.of(2018, 12, 10), ENTRIES, ISSUE, SELLER,
            BUYER));
    assertEquals("Passing date cannot be in the future", exception.getMessage());
  }
}
