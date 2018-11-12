package pl.coderstrust.database.file;

import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IdGenerator {

  private String idFileName;

  public IdGenerator(String idFileName) throws DatabaseOperationException {
    this.idFileName = idFileName;
    File file = new File(idFileName);
    try {
      if (!file.exists()) {
        FileWriter fileWriter = new FileWriter(idFileName);
        fileWriter.write(1L + System.getProperty("line.separator"));
        fileWriter.close();
      }
    } catch (IOException exception) {
      throw new DatabaseOperationException("idFile error");
    }
  }

  public long getNewId() throws DatabaseOperationException {
    long id;
    FileReader fileReader;
    Scanner scanner;
    try {
      fileReader = new FileReader(idFileName);
      scanner = new Scanner(fileReader);
      String line = (scanner.nextLine());
      id = Long.valueOf(line);
    } catch (FileNotFoundException ex) {
      throw new DatabaseOperationException("idFile error");
    }
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(idFileName);
      fileWriter.write(id + 1 + "");
      fileWriter.close();
    } catch (IOException exception) {
      throw new DatabaseOperationException("idFile error");
    }
    return id;
  }
}
