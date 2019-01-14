package pl.coderstrust.PDFcreator;

import com.itextpdf.text.DocumentException;
import pl.coderstrust.model.Address;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.model.InvoiceEntry;
import pl.coderstrust.model.Vat;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//todo Remove class
public class TempRun {

  public static void main(String[] args) throws DocumentException, FileNotFoundException {
    System.out.println("Test PDFcreator");
    PdfFactory pdf = new PdfFactory();

    List<InvoiceEntry> invoiceEntries = new ArrayList<>();
    InvoiceEntry invoiceEntry = new InvoiceEntry(1L, 5, "Computer"
        , "pcs", BigDecimal.valueOf(2999D), Vat.RATE_23, BigDecimal.valueOf(222.89)
        , BigDecimal.valueOf(122D));
    invoiceEntries.add(invoiceEntry);
    invoiceEntries.add(invoiceEntry);
    invoiceEntries.add(invoiceEntry);
    invoiceEntries.add(invoiceEntry);
    invoiceEntries.add(invoiceEntry);
    invoiceEntries.add(invoiceEntry);

    Address address = new Address(55L, "Warszawska", "158C/12", "Zielona Gora"
        , "99-999");
    Company company = new Company(12L, "Komputronik", address
        , "6431398097", "12 1234 1234 1234 1234 1234 1234");
    Invoice invoice = new Invoice(1L, LocalDate.now(), invoiceEntries, "159/01/2019", company,
        company);

    pdf.saveInvoiceInFile(invoice);

  }

}
