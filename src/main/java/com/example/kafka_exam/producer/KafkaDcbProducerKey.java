package com.example.kafka_exam.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaDcbProducerKey {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("compression.type", "gzip");
        props.put("acks", "1");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "dcb-temp-topic";
        String oddKey = "1";
        String evenKey = "2";
        for(int i = 1; i < 11; i++) {
            if(i % 2 == 1) {
                producer.send(new ProducerRecord<>(topic, oddKey, String.format("%d - Apache Kafka is a distributed streaming platform - key=" + oddKey, i)));
            } else {
                producer.send(new ProducerRecord<>(topic,  evenKey, String.format("%d - Apache Kafka is a distributed streaming platform - key=" + evenKey, i)));
            }
        }
        producer.close();
    }
}
