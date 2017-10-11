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
    private ResultSet result = null;

    private MySqlConnect() {
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"
                            + dataBase
                            + "?useUnicode=true&characterEncoding=utf-8&useSSL=false"
                    , userName
                    , passWord
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MySqlConnect(String sql) {
        this();
        try {
            this.stmt = this.conn.createStatement();
            this.result = this.stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (result != null || stmt != null) {
            try {
                result.close();
                stmt.close();
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            } finally {
                result = null;
                stmt = null;
            }
        }
    }

    public ResultSet getResult() {
        return this.result;
    }
}
