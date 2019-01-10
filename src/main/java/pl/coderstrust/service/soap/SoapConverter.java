package pl.coderstrust.service.soap;

import pl.coderstrust.service.soap.bindingClasses.Address;
import pl.coderstrust.service.soap.bindingClasses.Company;
import pl.coderstrust.service.soap.bindingClasses.Invoice;
import pl.coderstrust.service.soap.bindingClasses.InvoiceEntry;
import pl.coderstrust.service.soap.bindingClasses.Vat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class SoapConverter {

  pl.coderstrust.model.Invoice soapInvoiceToInvoice(Invoice soapInvoice) {
    Long id = soapInvoice.getId();
    LocalDate issueDate = unmarshal(soapInvoice.getIssueDate());
    List<pl.coderstrust.model.InvoiceEntry> entries = new ArrayList<>();
    for (InvoiceEntry soapEntry : soapInvoice.getEntries()) {
      entries.add(soapInvoiceEntryToInvoiceEntry(soapEntry));
    }
    String issue = soapInvoice.getIssue();
    pl.coderstrust.model.Company seller = soapCompanyToCompany(soapInvoice.getSeller());
    pl.coderstrust.model.Company buyer = soapCompanyToCompany(soapInvoice.getBuyer());
    return new pl.coderstrust.model.Invoice(id, issueDate, entries, issue, seller, buyer);
  }

  Invoice invoiceToSoapInvoice(pl.coderstrust.model.Invoice invoice) {
    Invoice soapInvoice = new Invoice();
    soapInvoice.setId(invoice.getId());
    soapInvoice.setIssueDate(marshal(invoice.getIssueDate()));
    soapInvoice.setIssue(invoice.getIssue());
    soapInvoice.setBuyer(companyToSoapCompany(invoice.getBuyer()));
    soapInvoice.setSeller(companyToSoapCompany(invoice.getSeller()));
    for (pl.coderstrust.model.InvoiceEntry entry : invoice.getEntries()) {
      soapInvoice.getEntries().add(invoiceEntryToSoapInvoiceEntry(entry));
    }
    return soapInvoice;
  }

  private InvoiceEntry invoiceEntryToSoapInvoiceEntry(
      pl.coderstrust.model.InvoiceEntry invoiceEntry) {
    InvoiceEntry soapInvoiceEntry = new InvoiceEntry();
    soapInvoiceEntry.setId(invoiceEntry.getId());
    soapInvoiceEntry.setAmount(invoiceEntry.getAmount());
    soapInvoiceEntry.setProductName(invoiceEntry.getProductName());
    soapInvoiceEntry.setUnit(invoiceEntry.getUnit());
    soapInvoiceEntry.setPrice(invoiceEntry.getPrice());
    soapInvoiceEntry.setVatRate(Vat.valueOf(invoiceEntry.getVatRate().toString()));
    soapInvoiceEntry.setNetValue(invoiceEntry.getNetValue());
    soapInvoiceEntry.setGrossValue(invoiceEntry.getGrossValue());
    return soapInvoiceEntry;
  }

  private Company companyToSoapCompany(pl.coderstrust.model.Company company) {
    Company soapCompany = new Company();
    soapCompany.setCompanyId(company.getCompanyId());
    soapCompany.setCompanyName(company.getCompanyName());
    soapCompany.setAddress(addressToSoapAddress(company.getAddress()));
    soapCompany.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
    soapCompany.setAccountNumber(company.getAccountNumber());
    return soapCompany;
  }

  private Address addressToSoapAddress(pl.coderstrust.model.Address address) {
    Address soapAddress = new Address();
    soapAddress.setId(address.getId());
    soapAddress.setStreet(address.getStreet());
    soapAddress.setNumber(address.getNumber());
    soapAddress.setCity(address.getCity());
    soapAddress.setZipCode(address.getZipCode());
    return soapAddress;
  }

  private pl.coderstrust.model.InvoiceEntry soapInvoiceEntryToInvoiceEntry(
      InvoiceEntry soapInvoiceEntry) {
    Long id = soapInvoiceEntry.getId();
    double amount = soapInvoiceEntry.getAmount();
    String productName = soapInvoiceEntry.getProductName();
    String unit = soapInvoiceEntry.getUnit();
    BigDecimal price = soapInvoiceEntry.getPrice();
    pl.coderstrust.model.Vat vatRate = pl.coderstrust.model.Vat
        .valueOf(soapInvoiceEntry.getVatRate().value());
    BigDecimal netValue = soapInvoiceEntry.getNetValue();
    BigDecimal grossValue = soapInvoiceEntry.getGrossValue();
    return new pl.coderstrust.model.InvoiceEntry(id, amount, productName, unit, price, vatRate,
        netValue, grossValue);
  }

  private pl.coderstrust.model.Company soapCompanyToCompany(Company soapCompany) {
    Long id = soapCompany.getCompanyId();
    String companyName = soapCompany.getCompanyName();
    pl.coderstrust.model.Address address = soapAddressToAddress(soapCompany.getAddress());
    String taxIdentificationNumber = soapCompany.getTaxIdentificationNumber();
    String accountNumber = soapCompany.getAccountNumber();
    return new pl.coderstrust.model.Company(id, companyName, address,
        taxIdentificationNumber, accountNumber);
  }

  private pl.coderstrust.model.Address soapAddressToAddress(Address soapAddress) {
    Long id = soapAddress.getId();
    String street = soapAddress.getStreet();
    String number = soapAddress.getNumber();
    String city = soapAddress.getCity();
    String zipCode = soapAddress.getZipCode();
    return new pl.coderstrust.model.Address(id, street, number,
        city, zipCode);
  }

  protected LocalDate unmarshal(String date) {
    return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
  }

  protected String marshal(LocalDate localDate) {
    return localDate.format(DateTimeFormatter.ISO_DATE);
  }
}
