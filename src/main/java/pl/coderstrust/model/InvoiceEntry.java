package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class InvoiceEntry {

  private final double amount;
  private final String productName;
  private final String unit;
  private final BigDecimal price;
  private final Vat vatRate;
  private final BigDecimal netValue;
  private final BigDecimal grossValue;

  @JsonCreator
  public InvoiceEntry(@JsonProperty("amount") double amount,
      @JsonProperty("productName") String productName,
      @JsonProperty("unit") String unit,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("vatRate") Vat vatRate,
      @JsonProperty("netValue") BigDecimal netValue,
      @JsonProperty("grossValue") BigDecimal grossValue) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount must be greater than 0");
    }
    this.amount = amount;
    this.productName = productName;
    this.unit = unit;
    this.price = price;
    this.vatRate = vatRate;
    this.netValue = netValue;
    this.grossValue = grossValue;
  }

  double getAmount() {
    return amount;
  }

  String getProductName() {
    return productName;
  }

  String getUnit() {
    return unit;
  }

  BigDecimal getPrice() {
    return price;
  }

  Vat getVatRate() {
    return vatRate;
  }

  BigDecimal getNetValue() {
    return netValue;
  }

  BigDecimal getGrossValue() {
    return grossValue;
  }
}
