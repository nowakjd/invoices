package pl.coderstrust.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class InvoiceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private InvoiceService invoiceService;

  private Invoice invoice1;
  private Invoice invoice2;
  private List<Invoice> invoiceList;

  @BeforeAll
  void setUp() {
    invoice1 = new Invoice(1, LocalDate.of(2018, 9, 1),
        new ArrayList<>(), "FA/333/2018", null, null);
    invoice2 = new Invoice(2, LocalDate.of(2018, 10, 1),
        new ArrayList<>(), "FA/333/2018", null, null);
    invoiceList = Arrays.asList(invoice1, invoice2);
  }

  @Test
  @DisplayName("Checking the save method call")
  void saveMethodTest() throws Exception {
    given(invoiceService.save(invoice1)).willReturn(invoice1);

    mockMvc
        .perform(post("/invoices")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(toJson(invoice1)))
        .andExpect(status().isOk());

    then(invoiceService).should().save(invoice1);
  }

  @Test
  @DisplayName("Checking the findAll method call")
  void getFindAllTest() throws Exception {
    given(invoiceService.findAll()).willReturn(invoiceList);

    mockMvc
        .perform(get("/invoices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));

    then(invoiceService).should().findAll();
  }

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

  @Test
  @DisplayName("Checking the findByDate method call")
  void getFindByDateTest() throws Exception {
    given(invoiceService.findByDate(LocalDate.of(2018, 9, 1),
        LocalDate.of(2018, 11, 30))).willReturn(invoiceList);

    mockMvc
        .perform(get("/invoices/byDate?fromDate=2018-09-01&toDate=2018-11-30"))
        .andExpect(status().isOk());

    then(invoiceService).should()
        .findByDate(LocalDate.of(2018, 9, 1),
            LocalDate.of(2018, 11, 30));
  }

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
