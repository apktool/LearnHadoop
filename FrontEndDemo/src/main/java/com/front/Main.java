/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 10/10/17
 */
package com.front;

import com.itemcf.AnimeRecommend;
import com.kafka.ConsumerDemo;
import com.kafka.ProducerDemo;
import com.mysql.MySqlConnect;
import com.thread.MyThread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static HashMap<Integer, ArrayList<String>> formatResult(ResultSet result) throws SQLException {
        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        while (result.next()) {
            Integer mid = result.getInt("mid");
            String seasonID = result.getString("season_id");

            ArrayList<String> value = map.containsKey(mid) ? map.get(mid) : new ArrayList<String>();
            value.add(seasonID);
            map.put(mid, value);
        }
        return map;
    }

    public static void createProducer(HashMap<Integer, ArrayList<String>> map) {
        ProducerDemo producer = new ProducerDemo();
        producer.setProducer(map);
    }

    public static void createConsumer(){
        ConsumerDemo consumer = new ConsumerDemo();
        consumer.getConsumer();
    }

    public static void main(String[] args) throws SQLException, NoSuchMethodException, URISyntaxException, ClassNotFoundException, InterruptedException, IOException {
        String sql = "select mid, season_id from bilibili_anime_info limit 100";
        MySqlConnect mysql = new MySqlConnect(sql);
        ResultSet result = mysql.getResult();

        /**
         * 使用Java反射机制实现将函数作为参数传递给另一个函数
         * 主要是为了能够将producer和consumer并发执行
         */
        Main hana = new Main();
        Method methodProducer = Main.class.getMethod("createProducer", HashMap.class);
        MyThread threadProducer = new MyThread();
        threadProducer.start(hana, methodProducer, formatResult(result));
        System.out.println("-producer->| " + threadProducer.getId());

        Method methodConsumer = Main.class.getMethod("createConsumer");
        MyThread threadConsumer = new MyThread();
        threadConsumer.start(hana, methodConsumer);
        System.out.println("-consumer->| " + threadConsumer.getId());

        /**
         * 留20s的时间给kafka consumer，让kafka consumer给consumerResult赋值，然后从里面取出来打印输出
         */
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Writer writer = new FileWriter("src/main/resources/uid_to_bid.csv");
        for (HashMap.Entry<Integer, ArrayList<String>> entry: ConsumerDemo.consumerResult.entrySet()){
            Integer key = entry.getKey();
            ArrayList<String> values = entry.getValue();
            for (String value: values) {
                writer.append(Integer.toString(key) + "," + value + "," + "1\n");
                writer.flush();
                // System.out.format("%d : %s\n", key, value);
            }
        }
        writer.close();

        AnimeRecommend animeRecommend = new AnimeRecommend();
        animeRecommend.start();
    }
}
