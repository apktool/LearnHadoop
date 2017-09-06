/**
 * Description
 * Create database using Hive
 *
 * @author apktool
 * @version 0.1
 * @since 8/31/17
 */

import org.apache.log4j.BasicConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveCreateDb {
    // private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String driverName = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        BasicConfigurator.configure();

        Class.forName(driverName);
        Connection con = null;
        try {
            // Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "hadoop", "hive");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/metastore?" +
                            "useUnicode=true&characterEncoding=utf-8&useSSL=false",
                    "hiveuser", "hivepassword");
            Statement stmt = con.createStatement();

            stmt.executeUpdate("CREATE DATABASE userdb");
            System.out.println("Database userdb created successfully.");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            con.close();

        }
    }
}
