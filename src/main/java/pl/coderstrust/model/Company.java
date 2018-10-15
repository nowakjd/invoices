package pl.coderstrust.model;

import pl.coderstrust.service.StringValidator;

class Company {

  private int companyId;
  private String companyName;
  private Address address;
  private String taxIdentificationNumber;
  private String accountNumber;

  int getCompanyId() {
    return companyId;
  }

  void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  String getCompanyName() {
    return companyName;
  }

  void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  Address getAddress() {
    return address;
  }

  void setAddress(Address address) {
    this.address = address;
  }

  String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  void setTaxIdentificationNumber(String taxIdentificationNumber) {
    StringValidator.validateTaxIdentificationNumber(taxIdentificationNumber);
    this.taxIdentificationNumber = taxIdentificationNumber;
  }

  String getAccountNumber() {
    return accountNumber;
  }

  void setAccountNumber(String accountNumber) {
    StringValidator.validateAccountNumber(accountNumber);
    this.accountNumber = accountNumber;

  }
}
