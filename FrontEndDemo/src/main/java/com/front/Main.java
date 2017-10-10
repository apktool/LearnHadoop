/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/10/17
 */
package com.front;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) throws SQLException {
        String sql = "select mid, title from bilibili_anime_info limit 5";
        MySqlConnect mysql = new MySqlConnect();
        ResultSet result = mysql.mysqlConn(sql);

        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        while (result.next()) {
            Integer mid = result.getInt("mid");
            String title = result.getString("title");

            ArrayList<String> value = map.containsKey(mid) ? map.get(mid) : new ArrayList<String>();
            value.add(title);
            map.put(mid, value);
        }
        mysql.mysqlClose();


        for (HashMap.Entry<Integer, ArrayList<String>> entry: map.entrySet()){
            Integer key = entry.getKey();
            ArrayList<String> values = entry.getValue();
            for (String value: values) {
                System.out.format("%d + %s\n", key, value);
            }
        }
    }
}
