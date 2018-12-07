package pl.coderstrust.database.file;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class FileProcessorTest {

  private static final String fileSeparator = System.getProperty("file.separator");
  private static final String resourcesPath =
      "src" + fileSeparator + "test" + fileSeparator
          + "resources" + fileSeparator;
  private final File actual = new File(resourcesPath + "actual");

  @Test
  void shouldSaveLines() throws DatabaseOperationException, IOException {
    File saveLineExpected = new File(resourcesPath + "saveLineExpected");
    FileProcessor fileProcessor = new FileProcessor(
        resourcesPath + "actual");
    fileProcessor.addLine("a");
    fileProcessor.addLine("a b");
    assertTrue(FileUtils.contentEqualsIgnoreEOL(saveLineExpected, actual,null));
    assertTrue(actual.delete());
  }

  @Test
  void shouldRemoveLine() throws DatabaseOperationException, IOException {
    File removeLinesStart = new File(resourcesPath + "removeLinesStart");
    FileUtils.copyFile(removeLinesStart, actual);
    FileProcessor fileProcessor = new FileProcessor(resourcesPath + "actual");
    fileProcessor.removeLine("abcd");
    File removeLinesExpected = new File(resourcesPath + "removeLinesExpected");
    assertTrue(FileUtils.contentEqualsIgnoreEOL(removeLinesExpected, actual,null));
    assertTrue(actual.delete());
  }

  @Test
  void shouldGetLines() throws IOException {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("a");
    expected.add("abc");
    expected.add("abcde");
    expected.add("abcdef");
    FileProcessor fileProcessor = new FileProcessor(resourcesPath + "getLinesStart");
    assertArrayEquals(expected.toArray(), fileProcessor.getLines().toArray());
  }
}
