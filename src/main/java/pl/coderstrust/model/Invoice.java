package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

  private long id;
  private LocalDate issueDate;
  private List<InvoiceEntry> entries;
  private String issue;
  private Company seller;
  private Company buyer;

  public Invoice() {
  }

  public Invoice(long id, LocalDate issueDate, List<InvoiceEntry> entries, String issue,
      Company seller, Company buyer) {
    if (id <= 0) {
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

  public long getId() {
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

    if (id != invoice.id) {
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
