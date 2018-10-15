package pl.coderstrust.model;

import pl.coderstrust.service.StringValidator;

import java.util.Objects;

public final class Address {

  private String street;
  private String number;
  private String city;
  private String zipCode;

  Address(String street, String number, String city, String zipCode) {
    this.street = street;
    this.number = number;
    this.city = city;
    StringValidator.validateZipCode(zipCode);
    this.zipCode = zipCode;
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
