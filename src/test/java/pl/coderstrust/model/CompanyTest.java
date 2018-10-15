package pl.coderstrust.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.PatternSyntaxException;

class CompanyTest {

  private static Company company;

  @BeforeEach
  void setup() {
    company = new Company();
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of account number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007", "54114020040000340277983541",
      "42 10901304 000000013142 0368"})
  void checkSetValidNrbNumber(String accountNrbNumber) {
    company.setAccountNumber(accountNrbNumber);
    assertEquals(accountNrbNumber, company.getAccountNumber());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of account NRB number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007 0000", "", "12566", "text123",
      "95_1140 2004 0000 3602 7660 2007", "PL95 1140 2004 0000 3602 7660 2007"})
  void checkSetInvalidNrbNumber(String accountNrbNumber) {
    assertThrows(PatternSyntaxException.class, () -> company.setAccountNumber(accountNrbNumber));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of tax identification number.")
  @ValueSource(strings = {"6570011469", "65-70-082-357", "526 103 77 37", "52 52 081 555"})
  void checkSetValidNipNumber(String taxIdentyficationNumber) {
    company.setTaxIdentificationNumber(taxIdentyficationNumber);
    assertEquals(taxIdentyficationNumber, company.getTaxIdentificationNumber());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of tax identification number.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460", "qwertyuiop"})
  void checkSetInvalidNipNumber(String nip) {
    assertThrows(PatternSyntaxException.class,
        () -> company.setTaxIdentificationNumber(nip));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of zip-code.")
  @ValueSource(strings = {"25-701", "00-001", "12-345", "44-666"})
  void checkSetValidZipCode(String zipCode) {
    Address address = new Address("Foo", "666", "foo", zipCode);
    assertEquals(zipCode, address.getZipCode());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of tax number NIP.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkSetInvalidZipCod(String zipCode) {
    assertThrows(PatternSyntaxException.class, () -> new Address("Foo", "666", "foo", zipCode));
  }

  @ParameterizedTest
  @DisplayName("Checking string getters and setters.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkStringGettersAndSetters(String strings) {
    company.setCompanyName(strings);
    Address address = new Address(strings, strings, strings, "02-066");
    assertAll(
        () -> assertEquals(strings, company.getCompanyName()),
        () -> assertEquals(strings, address.getCity()),
        () -> assertEquals(strings, address.getNumber()),
        () -> assertEquals(strings, address.getStreet())
    );
  }

  @ParameterizedTest
  @DisplayName("Checking integer getters and setters.")
  @ValueSource(ints = {12, 5689, 55, 666, 2018, 41258369, 22548899, 739010203})
  void checkIntegerGettersAndSetters(int number) {
    company.setCompanyId(number);
    assertAll(
        () -> assertEquals(number, company.getCompanyId())
    );
  }

  @Test
  @DisplayName("Checking integer getters and setters.")
  void checkAddressGetterAndSetter() {
    Address address = new Address("Wiejska", "12B/4", "Warszawa", "00-001");
    company.setAddress(address);
    Address address2 = new Address("Wiejska", "12B/4", "Warszawa", "00-002");
    assertNotEquals(address2, company.getAddress());
  }
}
