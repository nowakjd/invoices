package pl.coderstrust.database.infiledatabase;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class FileProcessorTest {

  @Test
  public void shouldSaveLines() throws DatabaseOperationException, IOException {
    File actual = new File("data-for-tests" + System.getProperty("file.separator") + "actual");
    actual.delete();
    File saveLineExpected = new File(
        "data-for-tests" + System.getProperty("file.separator") + "saveLineExpected");
    FileProcessor fileProcessor = new FileProcessor(
        "data-for-tests" + System.getProperty("file.separator") + "actual");
    fileProcessor.addLine("a");
    fileProcessor.addLine("a b");
    assertTrue(FileUtils.contentEquals(saveLineExpected, actual));
  }

  @Test
  public void shouldRemoveLine() throws DatabaseOperationException, IOException {
    File actual = new File("data-for-tests" + System.getProperty("file.separator") + "actual");
    File removeLinesStart = new File(
        "data-for-tests" + System.getProperty("file.separator") + "removeLinesStart");
    actual.delete();
    FileUtils.copyFile(removeLinesStart, actual);
    FileProcessor fileProcessor = new FileProcessor(
        "data-for-tests" + System.getProperty("file.separator") + "actual");
    fileProcessor.removeLine("abcd");
    File removeLinesExpected = new File(
        "data-for-tests" + System.getProperty("file.separator") + "removeLinesExpected");
    assertTrue(FileUtils.contentEquals(removeLinesExpected, actual));
  }

  @Test
  public void shouldGetLines() throws IOException {
    ArrayList<String> expected = new ArrayList<>();
    expected.add("a");
    expected.add("abc");
    expected.add("abcde");
    expected.add("abcdef");
    FileProcessor fileProcessor = new FileProcessor(
        "data-for-tests" + System.getProperty("file.separator") + "getLinesStart");
    assertArrayEquals(expected.toArray(), fileProcessor.getLines().toArray());
  }
}
