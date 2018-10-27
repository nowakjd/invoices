package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.coderstrust.service.StringValidator;

import java.util.Objects;

public class Address {

  private final String street;
  private final String number;
  private final String city;
  private final String zipCode;

  @JsonCreator
  public Address(@JsonProperty("street") String street, @JsonProperty("number") String number,
      @JsonProperty("city") String city, @JsonProperty("zipCode") String zipCode) {
    StringValidator.validateZipCode(zipCode);
    this.zipCode = zipCode;
    this.street = street;
    this.number = number;
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public String getNumber() {
    return number;
  }

  public String getCity() {
    return city;
  }

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
