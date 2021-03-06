package pl.coderstrust.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.coderstrust.model.Company;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class InvoiceControllerTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;

  private Company buyer;
  private Company seller;
  private Invoice invoice1;
  private Invoice invoice2;
  private List<Invoice> invoiceList;
  private List<Invoice> invoiceListByCompany;

  @BeforeAll
  void setUp() {
    buyer = new Company(41L, "Netflix", null,
        "6570011469", "11114015601081110181488249");
    seller = new Company(1L, "Microsoft", null, "5272830422",
        "11114015601081110181488249");
    invoice1 = new Invoice(1L, LocalDate.of(2018, 9, 1),
        new ArrayList<>(), "FA/333/2018", seller, buyer);
    invoice2 = new Invoice(2L, LocalDate.of(2018, 10, 1),
        new ArrayList<>(), "FA/444/2018", seller, buyer);
    invoiceList = Arrays.asList(invoice1, invoice2);
    invoiceListByCompany = Collections.singletonList(invoice1);
  }

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the save method call")
  void saveMethodTest() throws Exception {
    given(invoiceService.save(invoice1)).willReturn(invoice1);

    mockMvc
        .perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(toJson(invoice1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.issueDate", is("2018-09-01")))
        .andExpect(jsonPath("$.issue", is("FA/333/2018")))
        .andExpect(jsonPath("$.buyer.companyId", is(41)))
        .andExpect(jsonPath("$.buyer.companyName", is("Netflix")))
        .andExpect(jsonPath("$.buyer.taxIdentificationNumber", is("6570011469")))
        .andExpect(jsonPath("$.buyer.accountNumber", is("11114015601081110181488249")))
        .andExpect(jsonPath("$.seller.companyId", is(1)))
        .andExpect(jsonPath("$.seller.companyName", is("Microsoft")))
        .andExpect(jsonPath("$.seller.taxIdentificationNumber", is("5272830422")))
        .andExpect(jsonPath("$.seller.accountNumber", is("11114015601081110181488249")));

    then(invoiceService).should().save(invoice1);
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the findAll method call")
  void getFindAllTest() throws Exception {
    given(invoiceService.findAll()).willReturn(invoiceList);

    mockMvc
        .perform(get("/invoices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].issueDate", is("2018-09-01")))
        .andExpect(jsonPath("$[0].issue", is("FA/333/2018")))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[1].issueDate", is("2018-10-01")))
        .andExpect(jsonPath("$[1].issue", is("FA/444/2018")));

    then(invoiceService).should().findAll();
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the findById method call")
  void getFindByIdTest() throws Exception {
    given(invoiceService.findOne(1L)).willReturn(invoice1);

    mockMvc
        .perform(get("/invoices/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.issue", is("FA/333/2018")))
        .andExpect(jsonPath("$.issueDate", is("2018-09-01")));

    then(invoiceService).should().findOne(1L);
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the findByDate method call")
  void getFindByDateTest() throws Exception {
    given(invoiceService.findByDate(LocalDate.of(2018, 9, 1),
        LocalDate.of(2018, 10, 30))).willReturn(invoiceList);

    mockMvc
        .perform(get("/invoices/byDate?fromDate=2018-09-01&toDate=2018-10-30"))
        .andExpect(status().isOk());

    then(invoiceService).should()
        .findByDate(LocalDate.of(2018, 9, 1),
            LocalDate.of(2018, 10, 30));
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the findByBuyer method call")
  void getFindByBuyer() throws Exception {
    given(invoiceService.findByBuyer(41L)).willReturn(invoiceListByCompany);

    mockMvc
        .perform(get("/invoices/buyer/{companyId}", 41L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].buyer.companyId", is(41)))
        .andExpect(jsonPath("$[0].buyer.companyName", is("Netflix")))
        .andExpect(jsonPath("$[0].buyer.taxIdentificationNumber", is("6570011469")))
        .andExpect(jsonPath("$[0].buyer.accountNumber", is("11114015601081110181488249")));

    then(invoiceService).should().findByBuyer(41L);
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the findBySeller method call")
  void getBySeller() throws Exception {
    given(invoiceService.findBySeller(1L)).willReturn(invoiceListByCompany);

    mockMvc
        .perform(get("/invoices/seller/{companyId}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].seller.companyId", is(1)))
        .andExpect(jsonPath("$[0].seller.companyName", is("Microsoft")))
        .andExpect(jsonPath("$[0].seller.taxIdentificationNumber", is("5272830422")))
        .andExpect(jsonPath("$[0].seller.accountNumber", is("11114015601081110181488249")));

    then(invoiceService).should().findBySeller(1L);
  }

  @WithMockUser("admin")
  @Test
  @DisplayName("Checking the deleteById method call")
  void deleteByIdTest() throws Exception {
    willDoNothing().given(invoiceService).delete(1L);

    mockMvc
        .perform(delete("/invoices/{id}", 1))
        .andExpect(status().isOk());

    then(invoiceService).should().delete(1L);
  }

  private String toJson(Invoice invoice) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    return mapper.writeValueAsString(invoice);
  }
}
