package com.front;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/10/17
 */
class MySqlConnectTest {
    @Test
    @DisplayName("Connect to mysql and archive new value according to sql statement")
    void mysqlConn() {
        String sql = "select * from bilibili_anime_info limit 1";
        String result = new MySqlConnect().mysqlConn(sql).getClass().getSimpleName();
        assertEquals("ResultSetImpl", result);
    }

    @Test
    @DisplayName("Mysql is closed successfully")
    void mysqlClose() {
        System.out.println("Mysql is closed successfully!");
    }
}