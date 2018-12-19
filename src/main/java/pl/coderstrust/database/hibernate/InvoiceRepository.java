package pl.coderstrust.database.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.util.Collection;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  Collection<Invoice> findByBuyer(Company companyId) throws DatabaseOperationException;

  Collection<Invoice> findBySeller(Company companyId) throws DatabaseOperationException;
}
