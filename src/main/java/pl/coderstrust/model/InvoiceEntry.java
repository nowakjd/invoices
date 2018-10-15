package pl.coderstrust.model;

import java.math.BigDecimal;

public class InvoiceEntry {

  private String productName;
  private double amount;
  private String unit;
  private BigDecimal price;
  private Vat vatRate;
  private BigDecimal netValue;
  private BigDecimal grossValue;

  public InvoiceEntry() {
  }

  public InvoiceEntry(String productName, double amount, String unit, BigDecimal price,
      Vat vatRate, BigDecimal netValue, BigDecimal grossValue) {
    this.productName = productName;
    this.amount = amount;
    this.unit = unit;
    this.price = price;
    this.vatRate = vatRate;
    this.netValue = netValue;
    this.grossValue = grossValue;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount must be greater than 0");
    }
    this.amount = amount;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Vat getVatRate() {
    return vatRate;
  }

  public void setVatRate(Vat vatRate) {
    this.vatRate = vatRate;
  }

  public BigDecimal getNetValue() {
    return netValue;
  }

  public void setNetValue(BigDecimal netValue) {
    this.netValue = netValue;
  }

  public BigDecimal getGrossValue() {
    return grossValue;
  }

  public void setGrossValue(BigDecimal grossValue) {
    this.grossValue = grossValue;
  }
}
