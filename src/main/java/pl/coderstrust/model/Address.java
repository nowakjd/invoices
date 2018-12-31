package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.coderstrust.service.StringValidator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ApiModel(value = "AddressModel", description = "Sample address model")
@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ApiModelProperty(value = "The street where company is located", example = "Wall Street")
  private String street;
  @ApiModelProperty(value = "The number of building", example = "12B/134")
  private String number;
  @ApiModelProperty(value = "The city of company's headquarters", example = "New York")
  private String city;
  @ApiModelProperty(value = "The postal code of city", example = "12-999")
  private String zipCode;

  protected Address() {
  }

  @JsonCreator
  public Address(
      @JsonProperty("id") Long id,
      @JsonProperty("street") String street,
      @JsonProperty("number") String number,
      @JsonProperty("city") String city,
      @JsonProperty("zipCode") String zipCode) {
    StringValidator.validateZipCode(zipCode);
    this.zipCode = zipCode;
    this.id = id;
    this.street = street;
    this.number = number;
    this.city = city;
  }

  public Long getId() {
    return id;
  }

  // Public getters are requires in converting to JSONs by Object Mapper
  @SuppressWarnings("WeakerAccess")
  public String getStreet() {
    return street;
  }

  @SuppressWarnings("WeakerAccess")
  public String getNumber() {
    return number;
  }

  @SuppressWarnings("WeakerAccess")
  public String getCity() {
    return city;
  }

  @SuppressWarnings("WeakerAccess")
  public String getZipCode() {
    return zipCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Address address = (Address) obj;

    if (id != null ? !id.equals(address.id) : address.id != null) {
      return false;
    }
    if (street != null ? !street.equals(address.street) : address.street != null) {
      return false;
    }
    if (number != null ? !number.equals(address.number) : address.number != null) {
      return false;
    }
    if (city != null ? !city.equals(address.city) : address.city != null) {
      return false;
    }
    return zipCode != null ? zipCode.equals(address.zipCode) : address.zipCode == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (number != null ? number.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
    return result;
  }
}
