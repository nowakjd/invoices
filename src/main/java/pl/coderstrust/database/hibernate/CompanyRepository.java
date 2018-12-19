package pl.coderstrust.database.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderstrust.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
