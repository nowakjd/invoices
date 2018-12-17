package pl.coderstrust.database.file;

import pl.coderstrust.database.DatabaseOperationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

  private final String fileName;

  FileProcessor(String fileName) {
    this.fileName = fileName;
  }

  public void addLine(String line) throws DatabaseOperationException {
    try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName, true))
    ) {
      printWriter.println(line);
    } catch (IOException exc) {
      throw new DatabaseOperationException("Error during adding to file");
    }
  }

  public void removeLine(String lineToRemove) throws DatabaseOperationException {
    String lineFromFile;
    try (RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")) {
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        lineFromFile = randomAccessFile.readLine();
        if (lineFromFile.equals(lineToRemove)) {
          int spaceInFile = lineFromFile.getBytes().length;
          randomAccessFile.seek(randomAccessFile.getFilePointer() - 2);
          if (randomAccessFile.readByte() != '\r') {
            randomAccessFile.seek(randomAccessFile.getFilePointer() - spaceInFile);
          } else {
            randomAccessFile.seek(randomAccessFile.getFilePointer() - spaceInFile - 1);
          }
          @SuppressWarnings("ReplaceAllDot") String emptyline = lineFromFile.replaceAll(".", " ");
          randomAccessFile.writeBytes(emptyline);
        }
      }
    } catch (FileNotFoundException exception) {
      throw new DatabaseOperationException("Database not found", exception);
    } catch (IOException exception) {
      throw new DatabaseOperationException("Error during processing database", exception);
    }
  }

  public List<String> getLines() throws IOException {
    String line;
    List<String> lines = new ArrayList<>();
    try (
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(
            fileReader)) {
      while ((line = bufferedReader.readLine()) != null) {
        if (!line.substring(0, 1).equals(" ")) {
          lines.add(line);
        }
      }
    }
    return lines;
  }
}
