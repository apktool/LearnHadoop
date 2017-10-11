/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/10/17
 */

package com.mysql;

import java.sql.*;


public class MySqlConnect {
    private static String dataBase = "testDB";
    private static String userName = "python";
    private static String passWord = "python";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private ResultSet result = null;

    public ResultSet mysqlConn(String sql) {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"
                            + dataBase
                            + "?useUnicode=true&characterEncoding=utf-8&useSSL=false"
                    , userName
                    , passWord
            );
            this.stmt = this.conn.createStatement();
            // stmt = conn.prepareStatement(sql);
            // rs = stmt.getResultSet();
            this.result = this.stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void mysqlClose() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) { } // ignore
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) { } // ignore
            stmt = null;
        }
    }
}
