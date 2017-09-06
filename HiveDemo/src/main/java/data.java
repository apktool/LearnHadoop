import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

// assume that conn is an already created JDBC connection (see previous examples)

public class data {
    public static void main(String[] args) {

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            /*
            conn = DriverManager.getConnection("jdbc:mysql://localhost/metastore:3306?autoReconnect=true&useSSL=false" +
                    "user=hiveuser&password=hivepassword");
             */

            conn = DriverManager.getConnection("jdbc:mysql://localhost/metastore:3306?autoReconnect=true&useSSL=false",
                     "hiveuser", "hivepassword");
            System.out.println("Hello");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from TBLS");

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            // Now do something with the ResultSet ....
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }
}
