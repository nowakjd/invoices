package pl.coderstrust.PDFcreator;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.coderstrust.model.Invoice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

public class PdfFactory {

  void saveInvoice(Invoice invoice) throws DocumentException, FileNotFoundException {
    final String FILE_NAME = "faktura.pdf";
    final Font TEXT = FontFactory.getFont(FontFactory.TIMES, 10, BaseColor.BLACK);
    final Font TEXT_LARGE = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.BLACK);
    final Font TEXT_BOLD = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
    final Font TEXT_LARGE_BOLD = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
    final Font HEADER = FontFactory.getFont(FontFactory.TIMES_BOLD, 26, BaseColor.BLACK);
    final Font TEXT_SMALL = FontFactory.getFont(FontFactory.TIMES, 8, BaseColor.BLACK);
    final Font TEXT_SMALL_BOLD = FontFactory.getFont(FontFactory.TIMES_BOLD, 8, BaseColor.BLACK);
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));
    document.open();
    addHeader(HEADER, document);
    addNumberOfInvoice(invoice, TEXT_LARGE, document);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    addDateAndPlaceOfInvoice(invoice, TEXT, TEXT_BOLD, document);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    addSellerAndBuyer(invoice, TEXT, TEXT_LARGE_BOLD, document);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    addTableWithInvoiceEntries(invoice, TEXT_SMALL, TEXT_SMALL_BOLD, document);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    addTotalPriceValue(invoice, TEXT_LARGE_BOLD, document);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    addPlaceForSignature(TEXT_BOLD, document);
    document.close();
  }

  private void addPlaceForSignature(Font TEXT_BOLD, Document document) throws DocumentException {
    document.add(new Paragraph(
        "                                         Wystawil:                                                                                       Odebral:",
        TEXT_BOLD));
  }

  private void addTotalPriceValue(Invoice invoice, Font TEXT_LARGE_BOLD, Document document)
      throws DocumentException {
    String priceTotal = "Do zaplaty: " + getTotalPrice(invoice);
    document.add(new Paragraph(priceTotal, TEXT_LARGE_BOLD));
  }

  private void addTableWithInvoiceEntries(Invoice invoice, Font TEXT_SMALL, Font TEXT_SMALL_BOLD,
      Document document) throws DocumentException {
    PdfPTable table = new PdfPTable(9);
    table.setWidthPercentage(100);
    table.setWidths(new int[]{4, 40, 8, 8, 8, 8, 8, 8, 8});
    table.setKeepTogether(false);
    addHeaderRows(TEXT_SMALL_BOLD, table);
    addInvoiceEntriesRows(invoice, TEXT_SMALL, table);
    addBottomTotalValues(invoice, TEXT_SMALL, TEXT_SMALL_BOLD, table);
    document.add(table);
  }

  private void addBottomTotalValues(Invoice invoice, Font TEXT_SMALL, Font TEXT_SMALL_BOLD,
      PdfPTable table) {
    PdfPCell empty = new PdfPCell();
    empty.setBorder(Rectangle.NO_BORDER);
    table.addCell(empty);
    table.addCell(empty);
    table.addCell(empty);
    table.addCell(empty);
    PdfPCell totalText = new PdfPCell(new Paragraph("RAZEM:", TEXT_SMALL_BOLD));
    totalText.setBorder(Rectangle.NO_BORDER);
    table.addCell(totalText);
    PdfPCell netTotal = new PdfPCell(
        new Paragraph(getNetPriceTotal(invoice).toString(), TEXT_SMALL));
    table.addCell(netTotal);
    table.addCell(empty);
    table.addCell(totalText);
    PdfPCell netTotal2 = new PdfPCell(new Paragraph(getTotalPrice(invoice).toString(), TEXT_SMALL));
    table.addCell(netTotal2);
  }

  private BigDecimal getNetPriceTotal(Invoice invoice) {
    BigDecimal netPriceTotal = new BigDecimal(0);
    for (int i = 0; i < invoice.getEntries().size(); i++) {
      BigDecimal priceOneEntry = invoice.getEntries().get(i).getNetValue()
          .multiply(new BigDecimal(invoice.getEntries().get(i).getAmount()));
      netPriceTotal = netPriceTotal.add(priceOneEntry);
    }
    return netPriceTotal;
  }

  private BigDecimal getTotalPrice(Invoice invoice) {
    BigDecimal priceTotal = new BigDecimal(0);
    for (int i = 0; i < invoice.getEntries().size(); i++) {
      BigDecimal priceOneEntry = invoice.getEntries().get(i).getPrice()
          .multiply(new BigDecimal(invoice.getEntries().get(i).getAmount()));
      priceTotal = priceTotal.add(priceOneEntry);
    }
    return priceTotal;
  }

  private void addInvoiceEntriesRows(Invoice invoice, Font TEXT_SMALL, PdfPTable table) {
    for (int i = 0; i < invoice.getEntries().size(); i++) {
      PdfPCell numberEntryCell = new PdfPCell(new Paragraph(Integer.toString(i + 1), TEXT_SMALL));
      table.addCell(numberEntryCell);
      String productName = invoice.getEntries().get(i).getProductName();
      PdfPCell productNameCell = new PdfPCell(new Paragraph(productName, TEXT_SMALL));
      table.addCell(productNameCell);
      String amount = Double.toString(invoice.getEntries().get(i).getAmount());
      PdfPCell amountCell = new PdfPCell(new Paragraph(amount, TEXT_SMALL));
      table.addCell(amountCell);
      String unit = invoice.getEntries().get(i).getUnit();
      PdfPCell unitCell = new PdfPCell(new Paragraph(unit, TEXT_SMALL));
      table.addCell(unitCell);
      String netValue = invoice.getEntries().get(i).getNetValue().toString();
      PdfPCell netValueCell = new PdfPCell(new Paragraph(netValue, TEXT_SMALL));
      table.addCell(netValueCell);
      BigDecimal amountBigDecimal = new BigDecimal(amount);
      String netValueTotal = invoice.getEntries().get(i).getNetValue().multiply(amountBigDecimal)
          .toString();
      PdfPCell netValueTotalCell = new PdfPCell(new Paragraph(netValueTotal, TEXT_SMALL));
      table.addCell(netValueTotalCell);
      String vatRate = invoice.getEntries().get(i).getVatRate().getValue() * 100 + " %";
      PdfPCell vatRateCell = new PdfPCell(new Paragraph(vatRate, TEXT_SMALL));
      table.addCell(vatRateCell);
      BigDecimal vatPriceTotal = new BigDecimal(netValueTotal)
          .multiply(new BigDecimal(invoice.getEntries().get(i).getVatRate().getValue()));
      String vatPriceTotalRound = String.format("%.2f", vatPriceTotal.doubleValue());
      PdfPCell vatPriceTotalCell = new PdfPCell(new Paragraph(vatPriceTotalRound, TEXT_SMALL));
      table.addCell(vatPriceTotalCell);
      String price = invoice.getEntries().get(i).getPrice()
          .multiply(new BigDecimal(invoice.getEntries().get(i).getAmount())).toString();
      PdfPCell priceCell = new PdfPCell(new Paragraph(price, TEXT_SMALL));
      table.addCell(priceCell);
    }
  }

  private void addHeaderRows(Font TEXT_SMALL_BOLD, PdfPTable table) {
    String[] tableLabels = {"Lp.", "Nazwa", "ilosc", "j.m.", "jedn. netto", "Netto[zl]",
        "VAT[%]", "VAT [zl]", "Brutto[zl]"};
    for (int i = 0; i < 9; i++) {
      Paragraph paragraph = new Paragraph(tableLabels[i], TEXT_SMALL_BOLD);
      paragraph.setAlignment(Element.ALIGN_CENTER);
      PdfPCell cell = new PdfPCell(paragraph);
      cell.setBackgroundColor(new BaseColor(220, 220, 220));
      table.addCell(cell);
    }
  }

  private void addSellerAndBuyer(Invoice invoice, Font TEXT, Font TEXT_LARGE_BOLD,
      Document document) throws DocumentException {
    PdfPTable table = new PdfPTable(2);
    table.setWidthPercentage(100);
    addSeller(invoice, TEXT, TEXT_LARGE_BOLD, table);
    addBuyer(invoice, TEXT, TEXT_LARGE_BOLD, table);
    document.add(table);
  }

  private void addSeller(Invoice invoice, Font TEXT, Font TEXT_LARGE_BOLD, PdfPTable table) {
    Phrase seller = new Phrase("Sprzedawca:", TEXT);
    seller.add(Chunk.NEWLINE);
    seller.add(Chunk.NEWLINE);
    Chunk name = new Chunk(invoice.getSeller().getCompanyName(), TEXT_LARGE_BOLD);
    seller.add(name);
    seller.add(Chunk.NEWLINE);
    String street =
        invoice.getSeller().getAddress().getStreet() + " " + invoice.getSeller().getAddress()
            .getNumber();
    seller.add(street);
    seller.add(Chunk.NEWLINE);
    String postAndCity =
        invoice.getSeller().getAddress().getZipCode() + " " + invoice.getSeller().getAddress()
            .getCity();
    seller.add(postAndCity);
    seller.add(Chunk.NEWLINE);
    String taxNumber = "NIP: " + invoice.getSeller().getTaxIdentificationNumber();
    seller.add(taxNumber);
    seller.add(Chunk.NEWLINE);
    String accountNumber = "Nr konta: " + invoice.getSeller().getAccountNumber();
    seller.add(accountNumber);
    seller.add(Chunk.NEWLINE);
    seller.add(" ");
    PdfPCell cell = new PdfPCell(seller);
    cell.setBorder(0);
    table.addCell(cell);
  }

  private void addBuyer(Invoice invoice, Font TEXT, Font TEXT_LARGE_BOLD, PdfPTable table) {
    Phrase buyer = new Phrase("Nabywca:", TEXT);
    buyer.add(Chunk.NEWLINE);
    buyer.add(Chunk.NEWLINE);
    Chunk name = new Chunk(invoice.getBuyer().getCompanyName(), TEXT_LARGE_BOLD);
    buyer.add(name);
    buyer.add(Chunk.NEWLINE);
    String street =
        invoice.getBuyer().getAddress().getStreet() + " " + invoice.getBuyer().getAddress()
            .getNumber();
    buyer.add(street);
    buyer.add(Chunk.NEWLINE);
    String postAndCity =
        invoice.getBuyer().getAddress().getZipCode() + " " + invoice.getBuyer().getAddress()
            .getCity();
    buyer.add(postAndCity);
    buyer.add(Chunk.NEWLINE);
    String taxNumber = "NIP: " + invoice.getBuyer().getTaxIdentificationNumber();
    buyer.add(taxNumber);
    buyer.add(Chunk.NEWLINE);
    String accountNumber = "Nr konta: " + invoice.getBuyer().getAccountNumber();
    buyer.add(accountNumber);
    buyer.add(Chunk.NEWLINE);
    buyer.add(" ");
    PdfPCell cell = new PdfPCell(buyer);
    cell.setBorder(0);
    table.addCell(cell);
  }

  private void addDateAndPlaceOfInvoice(Invoice invoice, Font TEXT, Font TEXT_BOLD,
      Document document) throws DocumentException {
    String datePlaceOfInvoice =
        invoice.getSeller().getAddress().getCity() + ", " + invoice.getIssueDate();
    Chunk chunk1 = new Chunk("Miejsce i data wystawienia: ", TEXT);
    Chunk chunk2 = new Chunk(datePlaceOfInvoice, TEXT_BOLD);
    Paragraph paragraph = new Paragraph(chunk1);
    paragraph.add(chunk2);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    document.add(paragraph);
  }

  private void addNumberOfInvoice(Invoice invoice, Font TEXT_LARGE, Document document)
      throws DocumentException {
    Paragraph number = new Paragraph();
    number.setFont(TEXT_LARGE);
    number.setAlignment(Element.ALIGN_CENTER);
    number.add("nr: " + invoice.getIssue());
    document.add(number);
  }

  private void addHeader(Font HEADER, Document document) throws DocumentException {
    Paragraph title = new Paragraph();
    title.setFont(HEADER);
    title.setAlignment(Element.ALIGN_CENTER);
    title.add("Faktura VAT");
    document.add(title);
  }


}
