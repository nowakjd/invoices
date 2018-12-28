package pl.coderstrust.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import pl.coderstrust.database.hibernate.CompanyRepository;
import pl.coderstrust.database.hibernate.InvoiceRepository;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class HibernateDatabaseTest {

  @Mock
  private InvoiceRepository invoiceRepository;

  @Mock
  private CompanyRepository companyRepository;

  @InjectMocks
  private HibernateDatabase hibernateDatabase;

  private Company buyer;
  private Company seller;
  private Company buyer1;
  private Company seller1;
  private Invoice invoice1;
  private Invoice invoice2;
  private Invoice invoice3;
  private List<Invoice> invoiceList;
  private List<Invoice> invoiceListByCompany;
  private List<Invoice> invoiceListByDate;

  @BeforeAll
  void setUp() {
    buyer = new Company(null, "Netflix", null,
        "6570011469", "11114015601081110181488249");
    seller = new Company(null, "Microsoft", null, "5272830422",
        "11114015601081110181488249");
    buyer1 = new Company(1L, "Netflix", null,
        "6570011469", "11114015601081110181488249");
    seller1 = new Company(1L, "Microsoft", null, "5272830422",
        "11114015601081110181488249");
    invoice1 = new Invoice(null, LocalDate.of(2018, 9, 1),
        new ArrayList<>(), "FA/333/2018", seller, buyer);
    invoice2 = new Invoice(2L, LocalDate.of(2018, 10, 1),
        new ArrayList<>(), "FA/444/2018", seller, buyer);
    invoice3 = new Invoice(1L, LocalDate.of(2018, 9, 1),
        new ArrayList<>(), "FA/333/2018", seller1, buyer1);
    invoiceList = Arrays.asList(invoice2, invoice3);
    invoiceListByCompany = Collections.singletonList(invoice3);
    invoiceListByDate = Collections.singletonList(invoice3);
  }

  @Test
  @DisplayName("Save new invoice")
  void saveMethodTest() throws DatabaseOperationException {
    given(invoiceRepository.save(invoice1)).willReturn(invoice3);
    assertEquals(invoice3, hibernateDatabase.save(invoice1));
    then(invoiceRepository).should().save(invoice1);
  }

  @Test
  @DisplayName("Find invoice by id")
  void findByIdTest() throws DatabaseOperationException {
    given(invoiceRepository.findById(1L)).willReturn(Optional.ofNullable(invoice3));
    assertEquals(invoice3, hibernateDatabase.findOne(1L));
    then(invoiceRepository).should().findById(1L);
  }

  @Test
  @DisplayName("Find all invoices")
  void findAllTest() throws DatabaseOperationException {
    given(invoiceRepository.findAll()).willReturn(invoiceList);
    assertThat(hibernateDatabase.findAll()).isEqualTo(invoiceList);
    then(invoiceRepository).should().findAll();
  }

  @Test
  @DisplayName("Find invoices by buyer")
  void findByBuyer() throws DatabaseOperationException {
    given(companyRepository.findById(1L)).willReturn(Optional.ofNullable(buyer1));
    given(invoiceRepository.findByBuyer(buyer1)).willReturn(invoiceListByCompany);
    assertThat(hibernateDatabase.findByBuyer(1L)).isEqualTo(invoiceListByCompany);
    then(companyRepository).should().findById(1L);
    then(invoiceRepository).should().findByBuyer(buyer1);
  }

  @Test
  @DisplayName("Find invoices by seller")
  void findBySeller() throws DatabaseOperationException {
    given(companyRepository.findById(1L)).willReturn(Optional.ofNullable(seller1));
    given(invoiceRepository.findBySeller(seller1)).willReturn(invoiceListByCompany);
    assertThat(hibernateDatabase.findBySeller(1L)).isEqualTo(invoiceListByCompany);
    then(companyRepository).should().findById(1L);
    then(invoiceRepository).should().findBySeller(seller1);
  }

  @Test
  @DisplayName("Find invoices by date")
  void findByDate() throws DatabaseOperationException {
    given(invoiceRepository.findAll()).willReturn(invoiceListByDate);
    assertThat(hibernateDatabase.findByDate(LocalDate.of(2018, 9, 1),
        LocalDate.of(2018, 9, 30))).isEqualTo(invoiceListByDate);
    then(invoiceRepository).should().findAll();
  }

  @Test
  @DisplayName("Delete invoice by id")
  void deleteMethodTest() throws DatabaseOperationException {
    willDoNothing().given(invoiceRepository).deleteById(1L);
    hibernateDatabase.delete(1L);
    then(invoiceRepository).should().deleteById(1L);
  }

  @Test
  @DisplayName("Operations without existing id")
  void operationsWithoutExistingId() {
    willThrow(EmptyResultDataAccessException.class).given(invoiceRepository).deleteById(2L);
    willThrow(NoSuchElementException.class).given(invoiceRepository).findById(3L);
    assertThrows(DatabaseOperationException.class, () -> hibernateDatabase.delete(2L));
    assertThrows(DatabaseOperationException.class, () -> hibernateDatabase.findByBuyer(1L));
    assertThrows(DatabaseOperationException.class, () -> hibernateDatabase.findBySeller(4L));
    assertThrows(DatabaseOperationException.class, () -> hibernateDatabase.findOne(3L));
  }
}
