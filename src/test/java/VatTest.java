import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class VatTest {

  @Test
  void checkGetVatRates() {
    assertEquals(0, Vat.RATE_0.getValue());
    assertEquals(0.05f, Vat.RATE_5.getValue());
    assertEquals(0.08f, Vat.RATE_8.getValue());
    assertEquals(0.23f, Vat.RATE_23.getValue());
  }
}
