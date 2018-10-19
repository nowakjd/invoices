package pl.coderstrust.invoices.database;

import java.util.List;
import pl.coderstrust.invoices.model.Invoice;

public class InvoiceBook {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public void saveInvoice(Invoice invoice) {
    database.saveInvoice(invoice);
  }

  public List<Invoice> getInvoices() {
    return database.getInvoices();
  }

  public List<Invoice> updateInvoices() {
    return database.updateInvoices();
  }

  public void removeInvoiceById(Invoice invoice) {
    database.removeInvoiceById(invoice);
  }
}