package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ApiModel(value = "InvoiceEntryModel", description = "Sample InvoiceEntry model")
@Entity
public class InvoiceEntry {

  @ApiModelProperty(value = "The quantity of product", example = "2")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private double amount;
  @ApiModelProperty(value = "The name of product on invoice", example = "Lego")
  private String productName;
  @ApiModelProperty(value = "The unit of amount", example = "piece")
  private String unit;
  @ApiModelProperty(value = "The unit price o product", example = "200")
  private BigDecimal price;
  @ApiModelProperty(value = "The rate of value added tax",
      allowableValues = "RATE_0,RATE_5,RATE_8,RATE_23")
  @Enumerated(EnumType.STRING)
  private Vat vatRate;
  @ApiModelProperty(value = "The price of product without tax", example = "400")
  private BigDecimal netValue;
  @ApiModelProperty(value = "The price of product with tax", example = "432")
  private BigDecimal grossValue;

  protected InvoiceEntry() {
  }

  @JsonCreator
  public InvoiceEntry(
      @JsonProperty("id") Long id,
      @JsonProperty("amount") double amount,
      @JsonProperty("productName") String productName,
      @JsonProperty("unit") String unit,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("vatRate") Vat vatRate,
      @JsonProperty("netValue") BigDecimal netValue,
      @JsonProperty("grossValue") BigDecimal grossValue) {
    if (amount <= 0) {
      throw new IllegalArgumentException("The amount must be greater than 0");
    }
    this.id = id;
    this.amount = amount;
    this.productName = productName;
    this.unit = unit;
    this.price = price;
    this.vatRate = vatRate;
    this.netValue = netValue;
    this.grossValue = grossValue;
  }

  public Long getId() {
    return id;
  }

  public double getAmount() {
    return amount;
  }

  public String getProductName() {
    return productName;
  }

  public String getUnit() {
    return unit;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Vat getVatRate() {
    return vatRate;
  }

  public BigDecimal getNetValue() {
    return netValue;
  }

  public BigDecimal getGrossValue() {
    return grossValue;
  }
}
