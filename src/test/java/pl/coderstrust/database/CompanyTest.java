package pl.coderstrust.database;

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
  @DisplayName("Checking valid syntax of e-mail address.")
  @ValueSource(strings = {"name@company.com", "mail.box@address.com.pl", "test234@gmail.com"})
  void checkSetValidMailAddress(String email) {
    company.seteMail(email);
    assertEquals(email, company.geteMail());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of e-mail address.")
  @ValueSource(strings = {"name_company.com", "@op.pl", "name@company", "", "email:@company.it",
      "WallStreet12", "mike@cat@op.pl"})
  void checkSetInvalidMailAddress(String email) {
    assertThrows(PatternSyntaxException.class, () -> company.seteMail(email));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of account NRB number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007", "54114020040000340277983541",
      "42 10901304 000000013142 0368"})
  void checkSetValidNrbNumber(String accountNrbNumber) {
    company.setAccountNumberNrb(accountNrbNumber);
    assertEquals(removeWhiteSpaces(accountNrbNumber), company.getAccountNumberNrb());
  }

  private String removeWhiteSpaces(String number) {
    number = number.replaceAll(" ", "");
    number = number.replaceAll("-", "");
    return number;
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of account NRB number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007 0000", "", "12566", "text123",
      "95_1140 2004 0000 3602 7660 2007", "PL95 1140 2004 0000 3602 7660 2007"})
  void checkSetInvalidNrbNumber(String accountNrbNumber) {
    assertThrows(PatternSyntaxException.class, () -> company.setAccountNumberNrb(accountNrbNumber));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of account IBAN number.")
  @ValueSource(strings = {"PL671234 5678 0000 0000 1234 5678", "GE67123456780000000012345678",
      "IT671234 0000 9754 0000 8888 5678"})
  void checkSetValidIbanNumber(String accountIbanNumber) {
    company.setAccountNumberIban(accountIbanNumber);
    assertEquals(removeWhiteSpaces(accountIbanNumber), company.getAccountNumberIban());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of account IBAN number.")
  @ValueSource(strings = {"95 1140 2004 0000 3602 7660 2007 0000", "", "41949144949",
      "PLPL671234 5678 0000 0000 1234 5678", "some text"})
  void checkSetInvalidIbanNumber(String accountIbanNumber) {
    assertThrows(PatternSyntaxException.class,
        () -> company.setAccountNumberIban(accountIbanNumber));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of tax number NIP.")
  @ValueSource(strings = {"6570011469", "65-70-082-357", "526 103 77 37", "52 52 081 555"})
  void checkSetValidNipNumber(String nip) {
    company.setNip(nip);
    assertEquals(removeWhiteSpaces(nip), company.getNip());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of tax number NIP.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460", "qwertyuiop"})
  void checkSetInvalidNipNumber(String nip) {
    assertThrows(PatternSyntaxException.class,
        () -> company.setNip(nip));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of zip-code.")
  @ValueSource(strings = {"25-701", "00-001", "12-345", "44-666"})
  void checkSetValidZipCode(String zipCode) {
    Address address = new Address();
    address.setZipCode(zipCode);
    assertEquals(zipCode, address.getZipCode());
  }

  @ParameterizedTest
  @DisplayName("Checking invalid syntax of tax number NIP.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkSetInvalidZipCod(String zipCode) {
    Address address = new Address();
    assertThrows(PatternSyntaxException.class,
        () -> address.setZipCode(zipCode));
  }

  @ParameterizedTest
  @DisplayName("Checking valid syntax of Phone number.")
  @ValueSource(strings = {"413482544", "+48 22 256888888", "(041) 325 55 55"})
  void checkSetValidPhoneNumber(String phoneNumber) {
    company.setPhoneNumber(phoneNumber);
    assertEquals(phoneNumber, company.getPhoneNumber());
  }

  @ParameterizedTest
  @DisplayName("Checking string getters and setters.")
  @ValueSource(strings = {"", "1235454", "2018", "text", "6570011460"})
  void checkStringGettersAndSetters(String strings) {
    company.setCompanyName(strings);
    company.setPersonName(strings);
    company.setPersonSurname(strings);
    company.setPhoneNumber(strings);
    Address address = new Address();
    address.setCity(strings);
    address.setNumber(strings);
    address.setStreet(strings);
    assertAll(
        () -> assertEquals(strings, company.getCompanyName()),
        () -> assertEquals(strings, company.getPersonName()),
        () -> assertEquals(strings, company.getPersonSurname()),
        () -> assertEquals(strings, company.getPhoneNumber()),
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
    Address address = new Address();
    address.setStreet("Wiejska");
    address.setNumber("12B/4");
    address.setCity("Warszawa");
    address.setZipCode("00-001");
    company.setAddress(address);
    Address address2 = new Address();
    address2.setStreet("Wiejska");
    address2.setNumber("12B/4");
    address2.setCity("Warszawa");
    address2.setZipCode("00-002");
    assertNotEquals(address2, company.getAddress());
  }
}
