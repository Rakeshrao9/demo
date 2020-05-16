import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class FileJdbcRepository {

  public ResultSet update(List<String> listOfValues, Statement statement) throws SQLException {

       // String sql = "update ADJD_CLMHST_CLM " + " set partnId = "+ list.get(3)+  " where partnId = "+ list.get(0)
         //       + "and partVal = " + list.get(1) + " and invnCtlNbr = "+ list.get(2);


        String selectQuery = "SELECT * FROM T5410DBB.ADJD_CLMHST_CLM \n"
                + "WHERE PARTN_ID    IN       "+listOfValues +"\n"
                + "AND PARTN_VAL     = 'S523159745'       \n"
                + "AND INVN_CTL_NBR  = '7882089709'       \n"
                + "AND ICN_SUFX_CD   = '01'               \n"
                + "AND PROC_DT       = '2019-07-02'       \n"
                + "AND PROC_TM       = '05.36.40'         \n"
                + "AND ICN_SUFX_VERS_NBR = '1'";

        return statement.executeQuery(selectQuery);
    }

    public static Connection getDbConnection() throws Exception{
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        return DriverManager.getConnection("", "", "");
    }

}
