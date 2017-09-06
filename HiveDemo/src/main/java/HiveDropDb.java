/**
 * Description
 * Drop Database
 *
 * @author apktool
 * @version 0.1
 * @since 8/31/17
 */

import org.apache.log4j.BasicConfigurator;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

public class HiveDropDb {
    // private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        BasicConfigurator.configure();

        Class.forName(driverName);
        Connection con = null;

        try {
            // con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "hadoop", "hive");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/metastore?" +
                    "useUnicode=true&characterEncoding=utf-8&useSSL=false", "hiveuser", "hivepassword");
            // create statement
            Statement stmt = con.createStatement();

            // execute statement
            String sql = "DROP DATABASE userdb";
            stmt.execute(sql);

            System.out.println("Database userdb is dropped");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            con.close();
        }
    }
}

/*
    message:For direct MetaStore DB connections, we don't support retries at the client level.
 */
