package pl.coderstrust.service.soap;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.transform.Source;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class InvoicesEndpointTest {

  private static final String RESOURCES_PATH = "src/test/resources/SoapXmlRequests/";
  private static final int INVOICES_COUNT = 3;

  @Autowired
  private ApplicationContext applicationContext;
  private MockWebServiceClient mockClient;
  private Resource xsdSchema = new ClassPathResource("invoice.xsd");

  @BeforeEach
  void init() {
    mockClient = MockWebServiceClient.createClient(applicationContext);
  }

  @Test
  @DisplayName("Should add invoice")
  void saveInvoiceTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source saveResponsePayload = getRequest("saveInvoiceResponse.xml");

    //when
    mockClient
        .sendRequest(withPayload(saveRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(payload(saveResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  @DisplayName("Should get invoice by id")
  void getInvoiceByIdTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source getByIdRequestPayload = getRequest("getInvoiceByIdRequest.xml");
    mockClient
        .sendRequest(withPayload(saveRequestPayload))
        .andExpect(noFault());

    //when
    mockClient
        .sendRequest(withPayload(getByIdRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  @DisplayName("Should get all invoices")
  void getInvoicesTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source getAllRequestPayload = getRequest("getInvoicesRequest.xml");
    Source getAllResponsePayload = getRequest("getInvoicesResponse.xml");
    for (int i = 0; i < INVOICES_COUNT; i++) {
      mockClient
          .sendRequest(withPayload(saveRequestPayload))
          .andExpect(noFault());
    }

    //when
    mockClient
        .sendRequest(withPayload(getAllRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(payload(getAllResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  @DisplayName("Should get invoices by date")
  void getInvoicesByDateTest() throws IOException {
    //given
    Source saveChangedDateRequestPayload = getRequest("saveInvoiceRequestChangedDate.xml");
    Source getByDateRequestPayload = getRequest("getInvoiceByDateRequest.xml");
    Source getByDateResponsePayload = getRequest("getInvoiceByDateResponse.xml");
    mockClient
        .sendRequest(withPayload(saveChangedDateRequestPayload))
        .andExpect(noFault());

    //when
    mockClient
        .sendRequest(withPayload(getByDateRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(payload(getByDateResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  @DisplayName("Should delete invoice by id")
  void deleteInvoiceByIdTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source deleteRequestPayload = getRequest("deleteInvoiceByIdRequest.xml");
    mockClient
        .sendRequest(withPayload(saveRequestPayload))
        .andExpect(noFault());

    //when
    mockClient
        .sendRequest(withPayload(deleteRequestPayload))

        //then
        .andExpect(noFault());
  }

  @Test
  @DisplayName("Should get invoices by buyer")
  void getInvoicesByBuyerTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source getRequestPayload = getRequest("getInvoicesByBuyerRequest.xml");
    Source getResponsePayload = getRequest("getInvoicesByBuyerResponse.xml");
    for (int i = 0; i < INVOICES_COUNT; i++) {
      mockClient
          .sendRequest(withPayload(saveRequestPayload))
          .andExpect(noFault());
    }

    //when
    mockClient
        .sendRequest(withPayload(getRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(payload(getResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  @Test
  @DisplayName("Should get invoices by seller")
  void getInvoicesBySellerTest() throws IOException {
    //given
    Source saveRequestPayload = getRequest("saveInvoiceRequest.xml");
    Source getRequestPayload = getRequest("getInvoicesBySellerRequest.xml");
    Source getResponsePayload = getRequest("getInvoicesBySellerResponse.xml");
    for (int i = 0; i < INVOICES_COUNT; i++) {
      mockClient
          .sendRequest(withPayload(saveRequestPayload))
          .andExpect(noFault());
    }

    //when
    mockClient
        .sendRequest(withPayload(getRequestPayload))

        //then
        .andExpect(noFault())
        .andExpect(payload(getResponsePayload))
        .andExpect(validPayload(xsdSchema));
  }

  private String xmlFileReader(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
  }

  private Source getRequest(String fileName) throws IOException {
    String xmlRequest = xmlFileReader(RESOURCES_PATH + fileName);
    return new StringSource(xmlRequest);
  }
}
