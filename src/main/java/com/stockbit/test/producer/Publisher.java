package com.stockbit.test.producer;

import com.stockbit.test.config.CommonUtils;
import com.stockbit.test.constant.IKafkaConstants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Publisher {

    public static <T> void publishMessage(T payload, String key) {
        try {
            String jsonStock = CommonUtils.mapper().writeValueAsString(payload);
            Producer<String, String> producer = ProducerCreator.createProducer();
            ProducerRecord<String, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME, key, jsonStock);
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Send message: "+ record.key() + " " + record.value());
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
