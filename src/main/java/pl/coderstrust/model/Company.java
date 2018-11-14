package pl.coderstrust.model;

import pl.coderstrust.service.StringValidator;

public class Company {

  private final int companyId;
  private final String companyName;
  private final Address address;
  private final String taxIdentificationNumber;
  private final String accountNumber;

  public Company(int companyId, String companyName, Address address,
      String taxIdentificationNumber, String accountNumber) {
    this.companyId = companyId;
    this.companyName = companyName;
    this.address = address;
    StringValidator.validateTaxIdentificationNumber(taxIdentificationNumber);
    this.taxIdentificationNumber = taxIdentificationNumber;
    StringValidator.validateAccountNumber(accountNumber);
    this.accountNumber = accountNumber;
  }

  int getCompanyId() {
    return companyId;
  }

  String getCompanyName() {
    return companyName;
  }

  Address getAddress() {
    return address;
  }

  String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  String getAccountNumber() {
    return accountNumber;
  }
}
