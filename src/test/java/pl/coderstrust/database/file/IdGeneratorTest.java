package pl.coderstrust.database.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import pl.coderstrust.database.DatabaseOperationException;

import java.io.File;
import java.io.IOException;

class IdGeneratorTest {

  private String fileName =
      "src" + System.getProperty("file.separator") + "test" + System.getProperty("file.separator")
          + "resources" + System.getProperty("file.separator") + "id";
  private File database = new File(fileName);
  private File backup = new File(fileName + ".bak");

  @Test
  public void shouldReadFromFile() throws DatabaseOperationException, IOException {
    assertTrue(database.delete());
    FileUtils.copyFile(backup, database);
    IdGenerator idGenerator = new IdGenerator(fileName);
    assertEquals(5, idGenerator.getNewId());
    assertEquals(6, idGenerator.getNewId());
  }

  @Test
  public void shouldStartWithoutFile() throws DatabaseOperationException {
    assertTrue(database.delete());
    IdGenerator idGenerator = new IdGenerator(fileName);
    assertEquals(1, idGenerator.getNewId());
    assertEquals(2, idGenerator.getNewId());
  }
}
