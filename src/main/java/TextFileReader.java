import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TextFileReader {

  private static final String filePath = "../Demo 2/src/main/resources/records";

  private static final Integer tableNameLength = 50;

  private FileJdbcRepository repository = new FileJdbcRepository();

  public void fileReader() {

    Statement statement = null;

    Connection connection = null;

    try (Stream<String> stream = Files.lines(Paths.get(filePath)).parallel()) {
      List<String> list = stream.collect(Collectors.toList());

      connection = FileJdbcRepository.getDbConnection();
      log.info("successfully connected to DB2");
      statement = connection.createStatement();
      for (String line : list) {

        String tableName = line.substring(0, tableNameLength - 1).trim();
        String value = line.substring(tableNameLength);
        LinkedList<String> values = splitStringIntoSubStrings(value);
        if (values.isEmpty()) {
          log.warn("Unable to update record for table: {}, with record: {}", tableName, line);
        }
        updateRecords(values, tableName, statement);
      }
    } catch (IOException e) {
      log.warn("Unable to update record", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      log.warn("Unable to update record", e.getMessage());
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      }catch (Exception e){
        e.printStackTrace();
        log.warn("Unable to update record", e.getMessage());
      }
    }
  }

  private static LinkedList<String> splitStringIntoSubStrings(String parentStr) {
    return Arrays.stream(parentStr.split(","))
        .map(String::trim)
        .collect(Collectors.toCollection(LinkedList::new));
  }

  private void updateRecords(LinkedList<String> list, String tableName, Statement statement) {
    boolean flag = false;
    try {
      switch (tableName) {
        case "ADJD_CLMHST_CLM":
          ResultSet resultSet = repository.update(list, statement);
         int row = resultSet.getRow();
          log.info("row num " + row);
          resultSet.close();
          break;
        case "ADJD_CLMSF_BLK_A":

          break;

        default:
          // Statements
      }

    } catch (Exception e) {
      log.warn("Unable to update records");
    }

  }


}
