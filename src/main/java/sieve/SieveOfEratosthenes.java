package sieve;

import java.util.Arrays;

public class SieveOfEratosthenes {

  private static final int MARKER = 0;

  public static void main(String[] args) {
    int[] array = sieve(20);
    System.out.println(Arrays.toString(array));
  }

  public static int[] sieve(int maximumNumber) {
    int[] array = new int[maximumNumber];
    fillArrayWithNumbers(array);
    markNonPrimeNumbers(array);
    return extractPrimeNumbers(array);
  }

  private static void markNonPrimeNumbers(int[] array) {
    for (int j = 2; j < array.length; j++) {
      for (int i = j * 2; i < array.length; i += j) {
        if (array[i] % j == 0) {
          array[i] = MARKER;
        }
      }
    }
  }

  private static void fillArrayWithNumbers(int[] array) {
    for (int i = 2; i < array.length; i++) {
      array[i] = i;
    }
  }

  private static int[] extractPrimeNumbers(int[] array) {
    int[] arrayWithPrimeNumbers = new int[getCountOfPrimeNumber(array)];
    int arrayWithPrimeNumbersIndex = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] != MARKER) {
        arrayWithPrimeNumbers[arrayWithPrimeNumbersIndex] = array[i];
        arrayWithPrimeNumbersIndex++;
      }
    }
    return arrayWithPrimeNumbers;
  }

  private static int getCountOfPrimeNumber(int[] array) {
    int countOfPrimeNumbers = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] != MARKER) {
        countOfPrimeNumbers++;
      }
    }
    return countOfPrimeNumbers;
  }
}
