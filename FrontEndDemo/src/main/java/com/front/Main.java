/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/10/17
 */
package com.front;

import com.kafka.ConsumerDemo;
import com.kafka.ProducerDemo;
import com.mysql.MySqlConnect;
import com.thread.MyThread;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    private static HashMap<Integer, ArrayList<String>> formatResult(ResultSet result) throws SQLException {
        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        while (result.next()) {
            Integer mid = result.getInt("mid");
            String title = result.getString("title");

            ArrayList<String> value = map.containsKey(mid) ? map.get(mid) : new ArrayList<String>();
            value.add(title);
            map.put(mid, value);
        }
        return map;
    }

    public static void createProducer(HashMap<Integer, ArrayList<String>> map){
        ProducerDemo producer = new ProducerDemo();
        for (HashMap.Entry<Integer, ArrayList<String>> entry: map.entrySet()){
            Integer key = entry.getKey();
            ArrayList<String> values = entry.getValue();
            for (String value: values) {
                producer.sendRecorder("my-topic", key, value);
                System.out.format("%d + %s\n", key, value);
            }
        }
    }

    public static void createConsumer(){
        ConsumerDemo consumer = new ConsumerDemo();
        consumer.getConsumer();
    }

    public static void main(String[] args) throws SQLException, NoSuchMethodException {
        String sql = "select mid, title from bilibili_anime_info limit 5";
        MySqlConnect mysql = new MySqlConnect(sql);
        ResultSet result = mysql.getResult();

        HashMap<Integer, ArrayList<String>> map = formatResult(result);

        /**
         * 使用Java反射机制实现将函数作为参数传递给另一个函数
         * 主要是为了能够将producer和consumer并发执行
         */
        Main hana = new Main();
        Method methodProducer = Main.class.getMethod("createProducer", HashMap.class);
        MyThread threadProducer = new MyThread();
        threadProducer.run(hana, methodProducer, map);

        Method methodConsumer = Main.class.getMethod("createConsumer");
        MyThread threadConsumer = new MyThread();
        threadConsumer.run(hana, methodConsumer);
    }
}
