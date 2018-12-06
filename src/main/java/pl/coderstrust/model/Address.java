package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.coderstrust.service.StringValidator;

import java.util.Objects;

@ApiModel(value = "AddressModel", description = "Sample address model")
public class Address {

  @ApiModelProperty(value = "The street where company is located", example = "Wall Street")
  private final String street;
  @ApiModelProperty(value = "The number of building", example = "12B/134")
  private final String number;
  @ApiModelProperty(value = "The city of company's headquarters", example = "New York")
  private final String city;
  @ApiModelProperty(value = "The postal code of city", example = "12-999")
  private final String zipCode;

  @JsonCreator
  public Address(
      @JsonProperty("street") String street,
      @JsonProperty("number") String number,
      @JsonProperty("city") String city,
      @JsonProperty("zipCode") String zipCode) {
    StringValidator.validateZipCode(zipCode);
    this.zipCode = zipCode;
    this.street = street;
    this.number = number;
    this.city = city;
  }

  String getStreet() {
    return street;
  }

  String getNumber() {
    return number;
  }

  String getCity() {
    return city;
  }

  String getZipCode() {
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
    return Objects.equals(street, address.street)
        && Objects.equals(number, address.number)
        && Objects.equals(city, address.city)
        && Objects.equals(zipCode, address.zipCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, number, city, zipCode);
  }
}
