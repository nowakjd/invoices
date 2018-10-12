package pl.coderstrust.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Company {

  private int companyId;
  private String companyName;
  private String personName;
  private String personSurname;
  private Address address;
  private String nip;
  private String email;
  private String phoneNumber;
  private String accountNumberNrb;
  private String accountNumberIban;

  int getCompanyId() {
    return companyId;
  }

  void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  String getCompanyName() {
    return companyName;
  }

  void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  String getPersonName() {
    return personName;
  }

  void setPersonName(String personName) {
    this.personName = personName;
  }

  String getPersonSurname() {
    return personSurname;
  }

  void setPersonSurname(String personSurname) {
    this.personSurname = personSurname;
  }

  Address getAddress() {
    return address;
  }

  void setAddress(Address address) {
    this.address = address;
  }

  String getNip() {
    return nip;
  }

  void setNip(String nip) {
    nip = nip.replaceAll("-", "");
    nip = nip.replaceAll(" ", "");
    if (!isNipValid(nip)) {
      throw new java.util.regex.PatternSyntaxException("Syntax of NIP is invalid:", nip, -1);
    }
    this.nip = nip;
  }

  private boolean isNipValid(String nip) {
    if (nip.length() != 10) {
      return false;
    }
    int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
    try {
      int sum = 0;
      for (int i = 0; i < weights.length; i++) {
        sum += Integer.parseInt(nip.substring(i, i + 1)) * weights[i];
      }
      return (sum % 11) == Integer.parseInt(nip.substring(9, 10));
    } catch (NumberFormatException exception) {
      return false;
    }
  }

  String geteMail() {
    return email;
  }

  void seteMail(String email) {
    String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]"
        + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    if (!isValidByRegex(regex, email)) {
      throw new java.util.regex.PatternSyntaxException("Syntax of e-mail is invalid:", email, -1);
    }
    this.email = email;
  }

  String getPhoneNumber() {
    return phoneNumber;
  }

  void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  String getAccountNumberNrb() {
    return accountNumberNrb;
  }

  void setAccountNumberNrb(String accountNumberNrb) {
    accountNumberNrb = removeWhiteSpaces(accountNumberNrb);
    String regex = "(\\d{26})|((\\d{2} )(\\d{4} ){5}\\d{4})";
    if (!isValidByRegex(regex, accountNumberNrb)) {
      throw new java.util.regex.PatternSyntaxException("Syntax of NRB account number is invalid:",
          accountNumberNrb, -1);
    }
    this.accountNumberNrb = accountNumberNrb;
  }

  private String removeWhiteSpaces(String accountNumberNrb) {
    return accountNumberNrb.replaceAll(" ", "");
  }

  String getAccountNumberIban() {
    return accountNumberIban;
  }

  void setAccountNumberIban(String accountNumberIban) {
    accountNumberIban = removeWhiteSpaces(accountNumberIban);
    String regex = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}";
    if (!isValidByRegex(regex, accountNumberIban)) {
      throw new java.util.regex.PatternSyntaxException("Syntax of IBAN account number is invalid:",
          accountNumberIban, -1);
    }
    this.accountNumberIban = accountNumberIban;
  }


  private boolean isValidByRegex(String regex, String expression) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(expression);
    return matcher.matches();
  }
}
