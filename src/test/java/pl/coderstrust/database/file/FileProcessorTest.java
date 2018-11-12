package pl.coderstrust.database.file;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class FileProcessorTest {
  private static final String testResouserceDirectory =
      "src" + System.getProperty("file.separator") + "test" + System.getProperty("file.separator")
          + "resources" + System.getProperty("file.separator");

  @Test
  void shouldSaveLines() throws DatabaseOperationException, IOException {
    File actual = new File(testResouserceDirectory + "actual");
    assertTrue(actual.delete());
    File saveLineExpected = new File(testResouserceDirectory + "saveLineExpected");
    FileProcessor fileProcessor = new FileProcessor(
         testResouserceDirectory + "actual");
    fileProcessor.addLine("a");
    fileProcessor.addLine("a b");
    assertTrue(FileUtils.contentEquals(saveLineExpected, actual));
  }

  @Test
  void shouldRemoveLine() throws DatabaseOperationException, IOException {
    File actual = new File(testResouserceDirectory + "actual");
    File removeLinesStart = new File(testResouserceDirectory + "removeLinesStart");
    assertTrue(actual.delete());
    FileUtils.copyFile(removeLinesStart, actual);
    FileProcessor fileProcessor = new FileProcessor(testResouserceDirectory + "actual");
    fileProcessor.removeLine("abcd");
    File removeLinesExpected = new File(testResouserceDirectory + "removeLinesExpected");
    assertTrue(FileUtils.contentEquals(removeLinesExpected, actual));
  }

  @Test
  void shouldGetLines() throws IOException {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("a");
    expected.add("abc");
    expected.add("abcde");
    expected.add("abcdef");
    FileProcessor fileProcessor = new FileProcessor(testResouserceDirectory + "getLinesStart");
    assertArrayEquals(expected.toArray(), fileProcessor.getLines().toArray());
  }
}
