/**
 * Description
 * Consume given topic 
 *
 * @author apktool
 * @version 0.1
 * @since 9/12/17
 */

package com.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class ConsumerDemo {
    private static Consumer<Integer, String> consumer;
    public static HashMap<Integer, ArrayList<String>> consumerResult = new HashMap<Integer, ArrayList<String>>();

    private Consumer<Integer, String> configConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-consumer-group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return new KafkaConsumer<Integer, String>(props);
    }

    public ConsumerDemo(){
        consumer = this.configConsumer();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        consumer.close();
    }

    public void getConsumer() {
        consumer.subscribe(Arrays.asList("my-topic"));

        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(100);
            for (ConsumerRecord<Integer, String> record : records) {
                // System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                ArrayList<String> values = consumerResult.containsKey(record.key()) ? consumerResult.get(record.key()) : new ArrayList<String>();
                values.add(record.value());
                consumerResult.put(record.key(), values);
            }
        }
    }
}
