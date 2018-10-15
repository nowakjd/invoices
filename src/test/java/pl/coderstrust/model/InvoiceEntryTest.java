package pl.coderstrust.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class InvoiceEntryTest {

  private static final String PRODUCT_NAME = "Microwave";
  private static final double AMOUNT = 4;
  private static final String UNIT = "szt";
  private static final BigDecimal PRICE = BigDecimal.valueOf(125);
  private static final Vat VAT_RATE = null;
  private static final BigDecimal NET_VALUE = BigDecimal.valueOf(500);
  private static final BigDecimal GROSS_VALUE = BigDecimal.valueOf(165);

  @Test
  void testSetters() {
    InvoiceEntry entry = new InvoiceEntry();
    entry.setProductName(PRODUCT_NAME);
    entry.setAmount(AMOUNT);
    entry.setUnit(UNIT);
    entry.setPrice(PRICE);
    entry.setVatRate(VAT_RATE);
    entry.setNetValue(NET_VALUE);
    entry.setGrossValue(GROSS_VALUE);

    assertAll(
        () -> assertEquals(PRODUCT_NAME, entry.getProductName()),
        () -> assertEquals(AMOUNT, entry.getAmount()),
        () -> assertEquals(UNIT, entry.getUnit()),
        () -> assertEquals(PRICE, entry.getPrice()),
        () -> assertEquals(VAT_RATE, entry.getVatRate()),
        () -> assertEquals(NET_VALUE, entry.getNetValue()),
        () -> assertEquals(GROSS_VALUE, entry.getGrossValue())
    );
  }

  @Test
  void testGetters() {
    InvoiceEntry entry = new InvoiceEntry(PRODUCT_NAME, AMOUNT, UNIT, PRICE, VAT_RATE, NET_VALUE,
        GROSS_VALUE);

    assertAll(
        () -> assertEquals(PRODUCT_NAME, entry.getProductName()),
        () -> assertEquals(AMOUNT, entry.getAmount()),
        () -> assertEquals(UNIT, entry.getUnit()),
        () -> assertEquals(PRICE, entry.getPrice()),
        () -> assertEquals(VAT_RATE, entry.getVatRate()),
        () -> assertEquals(NET_VALUE, entry.getNetValue()),
        () -> assertEquals(GROSS_VALUE, entry.getGrossValue())
    );
  }

  @ParameterizedTest
  @DisplayName("Checking invalid value of amount")
  @ValueSource(doubles = {-1, 0})
  void checkSetInvalidAmount(double amount) {
    final InvoiceEntry entry = new InvoiceEntry();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> {
          entry.setAmount(amount);
        }
    );
    assertEquals("The amount must be greater than 0", exception.getMessage());
  }
}
