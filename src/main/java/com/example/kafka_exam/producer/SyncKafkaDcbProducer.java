package com.example.kafka_exam.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SyncKafkaDcbProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        try {
            RecordMetadata metadata = producer.send(new ProducerRecord<>("dcb-topic", "Apache kafka is  a distributed streaming platform(sync)")).get();
            System.out.printf("Partition: %d, Offset : %d", metadata.partition(), metadata.offset());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
