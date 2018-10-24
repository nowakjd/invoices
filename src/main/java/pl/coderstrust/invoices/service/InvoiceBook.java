package pl.coderstrust.invoices.service;

import java.util.Collection;
import pl.coderstrust.invoices.database.Database;

public class InvoiceBook<Invoice> {

  private Database database;

  public InvoiceBook(Database database) {
    this.database = database;
  }

  public void saveInvoice(Invoice invoice) {
    database.saveInvoice((pl.coderstrust.invoices.service.Invoice) invoice);
  }

  public Collection<Invoice> getInvoices() {
    return (Collection<Invoice>) database.getInvoices();
  }

  public void deleteInvoiceById() {
    database.deleteInvoice();
  }

}
