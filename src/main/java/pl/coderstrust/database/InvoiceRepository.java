package pl.coderstrust.database;

import org.springframework.data.repository.CrudRepository;
import pl.coderstrust.model.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
