package pl.coderstrust.database.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pl.coderstrust.model.Invoice;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public JsonConverter() {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  public String convert(Invoice invoice) throws JsonProcessingException {
    return objectMapper.writeValueAsString(invoice);
  }

  private Invoice convert(String line) throws IOException {
    return objectMapper.readValue(line, Invoice.class);
  }

  public List<Invoice> convert(List<String> lines) throws IOException {
    List<Invoice> result = new ArrayList<>();
    for (String line : lines) {
      result.add(convert(line));
    }
    return result;
  }
}
