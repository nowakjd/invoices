package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.List;

public class Invoice {

  private final long id;
  private final String issue;
  private final LocalDate issueDate;
  private final Company seller;
  private final Company buyer;
  private final List<InvoiceEntry> entries;


  public Invoice(long id, String issue, LocalDate issueDate, Company seller,
      Company buyer, List<InvoiceEntry> entries) {
    if (id <= 0) {
      throw new IllegalArgumentException("The id should be greater than zero");
    }
    this.id = id;
    this.issue = issue;
    if (issueDate.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Passing date cannot be in the future");
    }
    this.issueDate = issueDate;
    this.seller = seller;
    this.buyer = buyer;
    this.entries = entries;
  }

  public long getId() {
    return id;
  }

  public String getIssue() {
    return issue;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public Company getSeller() {
    return seller;
  }

  public Company getBuyer() {
    return buyer;
  }

  public List<InvoiceEntry> getEntries() {
    return entries;
  }
}
