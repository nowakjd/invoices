package pl.coderstrust.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import java.util.List;

class JsonConverterTest {

  private static final LocalDate date = LocalDate.parse("2018-11-11");
  private static Invoice invoice1;
  private static Invoice invoice2;
  private static JsonConverter jsonConverter;

  @BeforeAll
  static void setUp() {
    Address address = new Address(null,"Wall Street", "12/55B", "New York", "12-999");
    Company seller = new Company(1L, "Microsoft", address, "5272830422",
        "11114015601081110181488249");
    Company buyer = new Company(41L, "Netflix", address, "6570011469",
        "11114015601081110181488249");
    InvoiceEntry invoiceEntry1 = new InvoiceEntry(null,10, "Lego", "piece",
        new BigDecimal(199.99).setScale(2, BigDecimal.ROUND_HALF_EVEN),
        Vat.RATE_23,
        new BigDecimal(199.99), new BigDecimal(500));
    InvoiceEntry invoiceEntry2 = new InvoiceEntry(null,4, "Barbie", "piece",
        new BigDecimal(123.11).setScale(2, BigDecimal.ROUND_HALF_EVEN),
        Vat.RATE_8,
        new BigDecimal(399.99).setScale(2, BigDecimal.ROUND_HALF_EVEN), new BigDecimal(100));
    InvoiceEntry invoiceEntry3 = new InvoiceEntry(null,1, "Sand", "kg",
        new BigDecimal(19), Vat.RATE_0, new BigDecimal(99), new BigDecimal(12));
    List<InvoiceEntry> invoiceEntries = new ArrayList<>(
        Arrays.asList(invoiceEntry1, invoiceEntry2, invoiceEntry3));
    invoice1 = new Invoice(null, date, invoiceEntries, "FA/111/2018", seller, buyer);
    invoice2 = new Invoice(5L, date, invoiceEntries, "FA/111/2018", seller, buyer);
    jsonConverter = new JsonConverter();
  }

  @Test
  void shouldConvertIntoJsonAndIntoInvoice() throws IOException {
    List<String> jsonS = new ArrayList<>();
    jsonS.add(jsonConverter.convert(invoice1));
    jsonS.add(jsonConverter.convert(invoice2));
    List<Invoice> invoices = jsonConverter.convert(jsonS);
    assertEquals(invoice1, invoices.get(0));
    assertEquals(invoice2, invoices.get(1));
  }
}
