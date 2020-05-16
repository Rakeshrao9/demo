import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TextFileReader {

    private static final String filePath = "../TextFileParser/src/main/resources/records";

    private FileJdbcRepository repository = new FileJdbcRepository();

    public static void main(String[] args) {
        TextFileReader fileReader = new TextFileReader();
        fileReader.fileParser();
    }

    public void fileParser() {

        Statement statement = null;

        Connection connection = null;

        try (Stream<String> stream = Files.lines(Paths.get(filePath)).parallel()) {
            List<String> listOfValues = stream.collect(Collectors.toList());

            connection = FileJdbcRepository.getDbConnection();
            //log.info("successfully connected to DB2");
            statement = connection.createStatement();
           if(listOfValues.size() > 0){
            System.out.println("--elements--"+listOfValues.get(2));
               updateRecords(listOfValues, statement);
           }
        } catch (IOException e) {
            // log.warn("Unable to update record", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // log.warn("Unable to update record", e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
                // log.warn("Unable to update record", e.getMessage());
            }
        }
    }


    private void updateRecords(List<String> listOfValues, Statement statement) {
        try {
            ResultSet resultSet = repository.update(listOfValues, statement);

            int row = resultSet.getRow();
            System.out.println("--no of rows updated--"+row);

        } catch (Exception e) {
            //log.warn("Unable to update records");
        }

    }


}
