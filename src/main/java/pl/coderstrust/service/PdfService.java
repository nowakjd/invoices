package pl.coderstrust.service;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import pl.coderstrust.PDFcreator.PdfFactory;
import pl.coderstrust.model.Invoice;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletResponse;

@Service
public class PdfService {

  private PdfFactory pdfFactory = new PdfFactory();

  @Value("${pdf.filename}")
  private String FILE_NAME;

  public void saveToFile(HttpServletResponse response, Invoice invoice)
      throws IOException, DocumentException {
    pdfFactory.saveInvoiceInFile(invoice, FILE_NAME);
    File file = new File(FILE_NAME);
    if (file.exists()) {
      String mimeType = URLConnection.guessContentTypeFromName(file.getName());
      if (mimeType == null) {
        mimeType = "application/pdf";
      }
      response.setContentType(mimeType);
      response.setHeader("Content-Disposition",
          "attachment; filename=\"" + file.getName() + "\"");
      response.setContentLength((int) file.length());
      InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
      FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
  }

}
