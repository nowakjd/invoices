package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.coderstrust.service.StringValidator;

public class Company {

  private final Long companyId;
  private final String companyName;
  private final Address address;
  private final String taxIdentificationNumber;
  private final String accountNumber;

  @JsonCreator
  public Company(
      @JsonProperty("companyId") Long companyId,
      @JsonProperty("companyName") String companyName,
      @JsonProperty("address") Address address,
      @JsonProperty("taxIdentificationNumber") String taxIdentificationNumber,
      @JsonProperty("accountNumber") String accountNumber) {
    this.companyId = companyId;
    this.companyName = companyName;
    this.address = address;
    StringValidator.validateTaxIdentificationNumber(taxIdentificationNumber);
    this.taxIdentificationNumber = taxIdentificationNumber;
    StringValidator.validateAccountNumber(accountNumber);
    this.accountNumber = accountNumber;
  }

  public Long getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public Address getAddress() {
    return address;
  }

  public String getTaxIdentificationNumber() {
    return taxIdentificationNumber;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Company company = (Company) obj;

    if (companyId != null ? !companyId.equals(company.companyId) : company.companyId != null) {
      return false;
    }
    if (companyName != null ? !companyName.equals(company.companyName)
        : company.companyName != null) {
      return false;
    }
    if (address != null ? !address.equals(company.address) : company.address != null) {
      return false;
    }
    if (taxIdentificationNumber != null ? !taxIdentificationNumber
        .equals(company.taxIdentificationNumber) : company.taxIdentificationNumber != null) {
      return false;
    }
    return accountNumber != null ? accountNumber.equals(company.accountNumber)
        : company.accountNumber == null;
  }
}
