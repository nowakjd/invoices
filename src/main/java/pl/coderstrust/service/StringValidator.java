package pl.coderstrust.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringValidator {

  private static final String REGEX_ACCOUNT_NUMBER = "(\\d{26})|((\\d{2} )(\\d{4} ){5}\\d{4})";
  private static final String REGEX_ZIP_CODE = "[0-9]{2}-[0-9]{3}";
  private static StringValidator instance;

  private StringValidator() {
  }

  public static synchronized StringValidator getInstance() {
    if (instance == null) {
      instance = new StringValidator();
    }
    return instance;
  }

  public static void validateAccountNumber(String accountNumber) {
    accountNumber = removeWhiteSpaces(accountNumber);
    if (isValueNotMatch(REGEX_ACCOUNT_NUMBER, accountNumber)) {
      throw new PatternSyntaxException("Syntax of account number is invalid:", accountNumber, -1);
    }
  }

  public static void validateZipCode(String zipCode) {
    if (isValueNotMatch(REGEX_ZIP_CODE, zipCode)) {
      throw new PatternSyntaxException("Syntax of zip-code is invalid:", zipCode, -1);
    }
  }

  public static void validateTaxIdentificationNumber(String taxIdentificationNumber) {
    if (!isTaxIdentificationNumberValid(taxIdentificationNumber)) {
      throw new PatternSyntaxException("Syntax of tax identification number is invalid:",
          taxIdentificationNumber, -1);
    }
  }

  private static boolean isValueNotMatch(String regex, String expression) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(expression);
    return !matcher.matches();
  }

  private static String removeWhiteSpaces(String string) {
    string = string.replaceAll(" ", "");
    string = string.replaceAll("-", "");
    return string;
  }

  private static boolean isTaxIdentificationNumberValid(String taxIdentificationNumber) {
    taxIdentificationNumber = removeWhiteSpaces(taxIdentificationNumber);
    if (taxIdentificationNumber.length() != 10) {
      return false;
    }
    int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
    try {
      int sum = 0;
      for (int i = 0; i < weights.length; i++) {
        sum += Integer.parseInt(taxIdentificationNumber.substring(i, i + 1)) * weights[i];
      }
      return (sum % 11) == Integer.parseInt(taxIdentificationNumber.substring(9, 10));
    } catch (NumberFormatException exception) {
      return false;
    }
  }
}
