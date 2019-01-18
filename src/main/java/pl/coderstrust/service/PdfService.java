package pl.coderstrust.service;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import pl.coderstrust.model.Invoice;
import pl.coderstrust.pdfcreator.PdfFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

@Service
public class PdfService {

  private PdfFactory pdfFactory = new PdfFactory();

  @Value("${pdf.filename}")
  private String fileName;

  public void downloadPdfFile(HttpServletResponse response, Invoice invoice)
      throws IOException, DocumentException {
    ByteArrayOutputStream fileStreamOut = pdfFactory.savePdfInvoiceInMemory(invoice);
    byte[] buf = fileStreamOut.toByteArray();
    String mimeType = URLConnection.guessContentTypeFromName(fileName);
    if (mimeType == null) {
      mimeType = "application/pdf";
    }
    response.setContentType(mimeType);
    response.setHeader("Content-Disposition",
        "attachment; filename=\"" + fileName + "\"");
    InputStream inputStream = new ByteArrayInputStream(buf);
    FileCopyUtils.copy(inputStream, response.getOutputStream());
  }
}
