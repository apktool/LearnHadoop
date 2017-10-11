/**
 * Description
 * Produce new record related to given topic
 *
 * @author apktool
 * @version 0.1
 * @since 9/11/17
 */

package com.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerDemo {
    static private Producer<Integer, String> producer;

    private Producer<Integer, String> configProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<Integer, String>(props);
    }

    public ProducerDemo(){
        producer = this.configProducer();
    }

    @Override
    protected void finalize() throws Throwable {
        producer.close();
    }

    public void sendRecorder(String topic, Integer key, String value){
        producer.send(new ProducerRecord<Integer, String>(topic, key, value));
    }
}
