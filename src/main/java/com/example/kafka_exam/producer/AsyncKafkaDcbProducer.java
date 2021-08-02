package com.example.kafka_exam.producer;

import java.sql.SQLOutput;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class AsyncKafkaDcbProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        try {
            producer.send(new ProducerRecord<>("dcb-topic", "Apache kafka is  a distributed streaming platform(async)"), new ProducerCallback());
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }

    static class ProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if (metadata != null) {
                System.out.println("Partition : " + metadata.partition() + ", Offset : " + metadata.offset());
            } else {
                // 예외처리
                e.printStackTrace();
            }
        }
    }
}
