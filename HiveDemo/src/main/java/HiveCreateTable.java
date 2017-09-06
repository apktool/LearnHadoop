/**
 * Description
 * Create Table using Hive
 *
 * @author apktool
 * @version 0.1
 * @since 8/31/17
 */

import org.apache.log4j.BasicConfigurator;

import java.net.SocketPermission;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveCreateTable {
    // private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        BasicConfigurator.configure();

        Class.forName(driverName);
        Connection con = null;

        try {
            // Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb", "hadoop", "hive");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/metastore?" +
                    "useUnicode=true&characterEncoding=utf-8&useSSL=false", "hiveuser", "hivepassword");
            // create statement
            Statement stmt = con.createStatement();

            // execute statement
            String sql = "CREATE TABLE IF NOT EXISTS employee(" +
                    "eid int, " +
                    "name varchar(20)," +
                    "salary varchar(20)," +
                    "destignation varchar(20))";

            stmt.executeUpdate(sql);

            System.out.println("Table employee created.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            con.close();
        }
    }
}
