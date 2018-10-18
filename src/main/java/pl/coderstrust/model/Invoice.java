package pl.coderstrust.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

  private final long id;
  private final LocalDate issueDate;
  private final List<InvoiceEntry> entries;
  private final String issue;
  private final Company seller;
  private final Company buyer;

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
}
