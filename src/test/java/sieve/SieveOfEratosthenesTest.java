package sieve;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class SieveOfEratosthenesTest {

  @Parameters(method = "parametersForTheSieveTest")
  @Test
  public void shouldCalculateArrayOfThePrimeNumbersForProvidedValues(int size, int[] expected) {
    //when
    int[] array = SieveOfEratosthenes.sieve(size);
    //then
    assertThat(array, is(expected));
  }

  private Object[] parametersForTheSieveTest() {
    return
        new Object[]{
            new Object[]{30, new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29}
            },
            new Object[]{0, new int[]{}
            },
            new Object[]{1, new int[]{}
            }
        };
  }
}
