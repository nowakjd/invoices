package pl.coderstrust.database;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {

  private String street;
  private String number;
  private String city;
  private String zipCode;

  String getStreet() {
    return street;
  }

  void setStreet(String street) {
    this.street = street;
  }

  String getNumber() {
    return number;
  }

  void setNumber(String number) {
    this.number = number;
  }

  String getCity() {
    return city;
  }

  void setCity(String city) {
    this.city = city;
  }

  String getZipCode() {
    return zipCode;
  }

  void setZipCode(String zipCode) {
    String regex = "[0-9]{2}-[0-9]{3}";
    if (!isValidByRegex(regex, zipCode)) {
      throw new java.util.regex.PatternSyntaxException("Syntax of e-mail is invalid:", zipCode,
          -1);
    }
    this.zipCode = zipCode;
  }

  private boolean isValidByRegex(String regex, String expression) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(expression);
    return matcher.matches();
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
