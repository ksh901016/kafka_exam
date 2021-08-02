package com.example.kafka_exam.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaDcbProducerFor {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("acks", "1");
        props.put("compression.type", "gzip");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        System.out.println("========== START ==========");
        for(int i=1; i<200; i++){
            producer.send(new ProducerRecord<>("loss-message", "Apache kafka is  a distributed streaming platform - " + i));
            if(i == 5) {
                new Thread(() -> {
                    try {
                        System.out.println("========== THREAD START ==========");
                        String command = String.format("docker stop kafka-stack-docker-compose_kafka%s_1", "1");
                        Process p = Runtime.getRuntime().exec(command);
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        stdInput.lines().forEach(System.out::println);
                        System.out.println("========== THREAD END ==========");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            Thread.sleep(20);
        }
        System.out.println("========== END ==========");

        producer.close();
    }
}
