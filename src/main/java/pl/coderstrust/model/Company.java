package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.coderstrust.service.StringValidator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@ApiModel(value = "CompanyModel", description = "Sample company model")
@Entity
public class Company {

  @ApiModelProperty(value = "The id of company in the database", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long companyId;
  @ApiModelProperty(value = "The name of company", example = "Longnet")
  private String companyName;
  @ApiModelProperty(value = "The address of company's headquarter")
  @ManyToOne(cascade = CascadeType.ALL)
  private Address address;
  @ApiModelProperty(value = "The id number used for tax purposes", example = "5272830422")
  private String taxIdentificationNumber;
  @ApiModelProperty(value = "The number of bank's account",
      example = "11 1140 1560 1081 1101 8148 8249")
  private String accountNumber;

  protected Company() {
  }

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
