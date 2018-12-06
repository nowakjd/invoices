package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

@ApiModel(value = "InvoiceEntryModel", description = "Sample InvoiceEntry model")
public class InvoiceEntry {

  @ApiModelProperty(value = "The quantity of product", example = "2")
  private final double amount;
  @ApiModelProperty(value = "The name of product on invoice", example = "Lego")
  private final String productName;
  @ApiModelProperty(value = "The unit of amount", example = "piece")
  private final String unit;
  @ApiModelProperty(value = "The unit price o product", example = "200")
  private final BigDecimal price;
  @ApiModelProperty(value = "The rate of value added tax",
      allowableValues = "RATE_0,RATE_5,RATE_8,RATE_23")
  private final Vat vatRate;
  @ApiModelProperty(value = "The price of product without tax", example = "400")
  private final BigDecimal netValue;
  @ApiModelProperty(value = "The price of product with tax", example = "432")
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
