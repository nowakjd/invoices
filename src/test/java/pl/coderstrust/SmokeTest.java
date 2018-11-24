package pl.coderstrust;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.coderstrust.controller.InvoiceController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SmokeTest {

  @Autowired
  private InvoiceController invoiceController;

  @Test
  void contextLoads() {
    assertThat(invoiceController).isNotNull();
  }
}
