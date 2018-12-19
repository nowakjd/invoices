package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.coderstrust.service.StringValidator;

import java.util.Objects;
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

  public Long getId() {
    return id;
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
