package pl.coderstrust.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.IOException;

class IdGeneratorTest {

  private static final String fileSeparator = System.getProperty("file.separator");
  private static final String fileName =
      "src" + fileSeparator + "test" + fileSeparator
          + "resources" + fileSeparator + "id";
  private final File database = new File(fileName);
  private final File backup = new File(fileName + ".bak");

  @AfterEach
  void cleanUp() {
    assertTrue(database.delete());
  }

  @Test
  void shouldReadFromFile() throws DatabaseOperationException, IOException {
    FileUtils.copyFile(backup, database);
    IdGenerator idGenerator = new IdGenerator(fileName);
    assertEquals(5, idGenerator.getNewId());
    assertEquals(6, idGenerator.getNewId());
  }

  @Test
  void shouldStartWithoutFile() throws DatabaseOperationException {
    IdGenerator idGenerator = new IdGenerator(fileName);
    assertEquals(1, idGenerator.getNewId());
    assertEquals(2, idGenerator.getNewId());
  }
}
