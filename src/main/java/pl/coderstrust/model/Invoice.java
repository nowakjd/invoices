package pl.coderstrust.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

  private final Long id;
  private final LocalDate issueDate;
  private final List<InvoiceEntry> entries;
  private final String issue;
  private final Company seller;
  private final Company buyer;

  @JsonCreator
  public Invoice(
      @JsonProperty("id") Long id,
      @JsonProperty("issueDate") LocalDate issueDate,
      @JsonProperty("entries") List<InvoiceEntry> entries,
      @JsonProperty("issue") String issue,
      @JsonProperty("seller") Company seller,
      @JsonProperty("buyer") Company buyer) {
    if ((id != null) && (id <= 0)) {
      throw new IllegalArgumentException("The id should be greater than zero");
    }
    if (issueDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Passing date cannot be in the future");
    }
    this.id = id;
    this.issueDate = issueDate;
    this.entries = entries != null ? new ArrayList(entries) : new ArrayList();
    this.issue = issue;
    this.seller = seller;
    this.buyer = buyer;
  }

  public Long getId() {
    return id;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }

  public String getIssue() {
    return issue;
  }

  public Company getSeller() {
    return seller;
  }

  public Company getBuyer() {
    return buyer;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Invoice invoice = (Invoice) obj;

    if (id != null ? !id.equals(invoice.id) : invoice.id != null) {
      return false;
    }
    if (issueDate != null ? !issueDate.equals(invoice.issueDate) : invoice.issueDate != null) {
      return false;
    }
    if (entries != null ? !entries.equals(invoice.entries) : invoice.entries != null) {
      return false;
    }
    if (issue != null ? !issue.equals(invoice.issue) : invoice.issue != null) {
      return false;
    }
    if (seller != null ? !seller.equals(invoice.seller) : invoice.seller != null) {
      return false;
    }
    return buyer != null ? buyer.equals(invoice.buyer) : invoice.buyer == null;
  }
}
