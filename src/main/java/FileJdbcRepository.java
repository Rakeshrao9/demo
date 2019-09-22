import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class FileJdbcRepository {



  public static void main(String[] args) throws Exception{

      Class.forName("com.ibm.db2.jcc.DB2Driver");
      Connection conn = DriverManager
          .getConnection("", "", "");
      ResultSet rs = conn.createStatement().executeQuery("select count(*) from ADJD_CLMHST_CLM");
      rs.next();
      int count = rs.getInt(1);
      System.out.println(count);
      rs.close();
      conn.close();

  }

}
