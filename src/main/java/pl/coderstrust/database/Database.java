package pl.coderstrust.database;

import pl.coderstrust.model.Invoice;

import java.util.Collection;

public interface Database {

  void saveInvoice(Invoice invoice);

  Collection<Invoice> getInvoices();

  void updateInvoice(Invoice invoice);

  void deleteInvoice(long id);
}
