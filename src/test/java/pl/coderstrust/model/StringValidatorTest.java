package pl.coderstrust.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.coderstrust.service.StringValidator;

import java.util.regex.PatternSyntaxException;

class StringValidatorTest {

  private static Address address;

  @BeforeEach
  void setUp() {
    address = new Address(1L, "Wiejska", "125/3B", "Warszawa", "01-159");
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of account number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007", "54114020040000340277983541",
      "42 10901304 000000013142 0368"})
  void checkSetValidNrbNumber(String accountNrbNumber) {
    Company company = new Company(125L, "Foo", address, "65-70-082-357", accountNrbNumber);
    assertEquals(accountNrbNumber, company.getAccountNumber());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of account NRB number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007 0000", "", "12566", "text123",
      "95_1140 2004 0000 3602 7660 2007", "PL95 1140 2004 0000 3602 7660 2007"})
  void checkSetInvalidNrbNumber(String accountNrbNumber) {
    assertThrows(PatternSyntaxException.class,
        () -> new Company(125L, "Foo", address, "65-70-082-357", accountNrbNumber));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of tax identification number.")
  @ValueSource(strings = {"6570011469", "65-70-082-357", "526 103 77 37", "52 52 081 555"})
  void checkSetValidNipNumber(String taxIdentyficationNumber) {
    Company company = new Company(125L, "Foo", address, taxIdentyficationNumber,
        "95 1140 2004 0000 3602 7660 2007");
    assertEquals(taxIdentyficationNumber, company.getTaxIdentificationNumber());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of tax identification number.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460", "qwertyuiop"})
  void checkSetInvalidNipNumber(String taxIdentyficationNumber) {
    assertThrows(PatternSyntaxException.class,
        () -> new Company(125L, "Foo", address, taxIdentyficationNumber,
            "95 1140 2004 0000 3602 7660 2007"));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of zip-code.")
  @ValueSource(strings = {"25-701", "00-001", "12-345", "44-666"})
  void checkSetValidZipCode(String zipCode) {
    Address address = new Address(1L, "Foo", "666", "foo", zipCode);
    assertEquals(zipCode, address.getZipCode());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of zip-code.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkSetInvalidZipCod(String zipCode) {
    assertThrows(PatternSyntaxException.class, () -> new Address(1L, "Foo", "666", "foo", zipCode));
  }

  @ParameterizedTest
  @DisplayName("Checking string getters and setters.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkStringGettersAndSetters(String strings) {
    Company company = new Company(111L, strings, address, "65 - 70 - 082 - 357",
        "54114020040000340277983541");
    Address address = new Address(1L, strings, strings, strings, "02-066");
    assertAll(
        () -> assertEquals(strings, company.getCompanyName()),
        () -> assertEquals(strings, address.getCity()),
        () -> assertEquals(strings, address.getNumber()),
        () -> assertEquals(strings, address.getStreet())
    );
  }

  @ParameterizedTest
  @DisplayName("Checking integer getters and setters.")
  @ValueSource(longs = {12, 5689, 55, 666, 2018, 41258369, 22548899, 739010203})
  void checkIntegerGettersAndSetters(Long number) {
    Company company = new Company(number, "Zus", address, "65 - 70 - 082 - 357",
        "54114020040000340277983541");
    Address address = new Address(number, "Wiejska", "12B/4", "Warszawa", "02-066");
    assertAll(
        () -> assertEquals(number, company.getCompanyId()),
        () -> assertEquals(number, address.getId())
    );
  }

  @Test
  @DisplayName("Checking integer getters and setters.")
  void checkAddressGetterAndSetter() {
    Company company = new Company(123L, "Zus", address, "65 - 70 - 082 - 357",
        "54114020040000340277983541");
    Address address2 = new Address(1L, "Wiejska", "12B/4", "Warszawa", "00-002");
    assertNotEquals(address2, company.getAddress());
    assertEquals(address, company.getAddress());
  }

  @Test
  @DisplayName("Check one instance of StringValidator")
  void checkSingleInstanceOfStringValidator() {
    StringValidator firstInstance = StringValidator.getInstance();
    StringValidator secondInstance = StringValidator.getInstance();
    assertSame(firstInstance, secondInstance);
  }
}
