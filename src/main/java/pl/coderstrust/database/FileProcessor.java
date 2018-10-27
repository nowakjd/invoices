package pl.coderstrust.database;

import pl.coderstrust.model.Invoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class FileProcessor {

  private String fileName;

  public FileProcessor(String fileName) {
    this.fileName = fileName;
  }

  public void addLine(String line) throws DatabaseOperationException {
    PrintWriter printWriter;
    try {
      printWriter = new PrintWriter(new FileWriter(fileName, true));
    } catch (IOException exc) {
      throw new DatabaseOperationException("error during adding to file");
    }
    printWriter.println(line);
    printWriter.close();

  }

  public void removeLine(String lineToRemove) throws DatabaseOperationException {
    String lineFromFile;
    try {
      RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
      while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        lineFromFile = randomAccessFile.readLine();
        if (lineFromFile.equals(lineToRemove)) {
          int spaceInFile = lineFromFile.getBytes().length;
          randomAccessFile.seek(randomAccessFile.getFilePointer() - spaceInFile - 1);
          randomAccessFile.writeBytes("empty");


        }
      }
    } catch (FileNotFoundException exception) {
      throw new DatabaseOperationException("database not found");
    } catch (IOException exception) {
      throw new DatabaseOperationException("error during processing database");
    }


  }

  public ArrayList<String> getLines() throws DatabaseOperationException {
    Scanner scanner;
    String line;
    try {
      scanner = new Scanner(new File(fileName));
    } catch (FileNotFoundException exception) {
      throw new DatabaseOperationException("database not found");
    }

    ArrayList<String> lines = new ArrayList<>();

    while (scanner.hasNextLine()) {
      line = scanner.nextLine();
      if (!line.substring(0, 5).equals("empty")) {
        lines.add(line);
      }
    }
    scanner.close();

    return lines;
  }

}
