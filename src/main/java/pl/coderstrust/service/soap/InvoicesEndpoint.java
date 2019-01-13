package pl.coderstrust.service.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.database.DatabaseOperationException;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.service.InvoiceService;
import pl.coderstrust.service.soap.bindingClasses.DeleteInvoiceByIdRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoiceByIdRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoiceByIdResponse;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesByBuyerRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesByBuyerResponse;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesByDateRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesByDateResponse;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesBySellerRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesBySellerResponse;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesRequest;
import pl.coderstrust.service.soap.bindingClasses.GetInvoicesResponse;
import pl.coderstrust.service.soap.bindingClasses.InvoicesList;
import pl.coderstrust.service.soap.bindingClasses.SaveInvoiceRequest;
import pl.coderstrust.service.soap.bindingClasses.SaveInvoiceResponse;

import java.time.LocalDate;
import java.util.Collection;

@Endpoint
public class InvoicesEndpoint {

  private static final String NAMESPACE_URI = "http://invoiceapp-service.com";
  private InvoiceService invoiceService;
  private SoapConverter converter;

  @Autowired
  public InvoicesEndpoint(InvoiceService invoiceService, SoapConverter converter) {
    this.invoiceService = invoiceService;
    this.converter = converter;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveInvoiceRequest")
  @ResponsePayload
  public SaveInvoiceResponse saveInvoice(@RequestPayload SaveInvoiceRequest request)
      throws DatabaseOperationException {
    Invoice invoice = converter.toInvoice(request.getInvoice());
    SaveInvoiceResponse response = new SaveInvoiceResponse();
    Invoice soapInvoice = invoiceService.save(invoice);
    response.setInvoice(converter.toSoapInvoice(soapInvoice));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoiceByIdRequest")
  @ResponsePayload
  public GetInvoiceByIdResponse getInvoiceById(@RequestPayload GetInvoiceByIdRequest request)
      throws DatabaseOperationException {
    Long id = request.getId();
    GetInvoiceByIdResponse response = new GetInvoiceByIdResponse();
    response.setInvoice(converter.toSoapInvoice(invoiceService.findOne(id)));
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesRequest")
  @ResponsePayload
  public GetInvoicesResponse getInvoices(@RequestPayload GetInvoicesRequest request)
      throws DatabaseOperationException {
    Collection<Invoice> invoices = invoiceService.findAll();
    InvoicesList soapInvoices = new InvoicesList();
    for (Invoice invoice : invoices) {
      soapInvoices.getInvoice().add(converter.toSoapInvoice(invoice));
    }
    GetInvoicesResponse response = new GetInvoicesResponse();
    response.setInvoicesList(soapInvoices);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesByDateRequest")
  @ResponsePayload
  public GetInvoicesByDateResponse getInvoicesByDate(
      @RequestPayload GetInvoicesByDateRequest request) throws DatabaseOperationException {
    LocalDate fromDate = converter.unmarshal(request.getFromDate());
    LocalDate toDate = converter.unmarshal(request.getToDate());
    Collection<Invoice> invoices = invoiceService.findByDate(fromDate, toDate);
    InvoicesList sopaInvoices = new InvoicesList();
    for (Invoice invoice : invoices) {
      sopaInvoices.getInvoice().add(converter.toSoapInvoice(invoice));
    }
    GetInvoicesByDateResponse response = new GetInvoicesByDateResponse();
    response.setInvoicesList(sopaInvoices);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceByIdRequest")
  @ResponsePayload
  public void deleteInvoiceById(@RequestPayload DeleteInvoiceByIdRequest request)
      throws DatabaseOperationException {
    Long id = request.getId();
    invoiceService.delete(id);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesByBuyerRequest")
  @ResponsePayload
  public GetInvoicesByBuyerResponse getInvoicesByBuyer(
      @RequestPayload GetInvoicesByBuyerRequest request) throws DatabaseOperationException {
    Long id = request.getCompanyId();
    Collection<Invoice> invoices = invoiceService.findByBuyer(id);
    InvoicesList soapInvoices = new InvoicesList();
    for (Invoice invoice : invoices) {
      soapInvoices.getInvoice().add(converter.toSoapInvoice(invoice));
    }
    GetInvoicesByBuyerResponse response = new GetInvoicesByBuyerResponse();
    response.setInvoicesList(soapInvoices);
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesBySellerRequest")
  @ResponsePayload
  public GetInvoicesBySellerResponse getInvoicesBySeller(
      @RequestPayload GetInvoicesBySellerRequest request) throws DatabaseOperationException {
    Long id = request.getCompanyId();
    Collection<Invoice> invoices = invoiceService.findBySeller(id);
    InvoicesList soapInvoices = new InvoicesList();
    for (Invoice invoice : invoices) {
      soapInvoices.getInvoice().add(converter.toSoapInvoice(invoice));
    }
    GetInvoicesBySellerResponse response = new GetInvoicesBySellerResponse();
    response.setInvoicesList(soapInvoices);
    return response;
  }
}
