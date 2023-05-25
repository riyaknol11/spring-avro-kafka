package com.knoldus.consumerconfig.service;

import com.knoldus.consumerconfig.config.KafkaConfig;
import com.knoldus.model.CoffeeOrder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;

@Service
public class CoffeeOrderService {
    private KafkaConfig kafkaConfig;

    @Autowired
    public CoffeeOrderService(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    private static final Logger log = LoggerFactory.getLogger(CoffeeOrderService.class);

    @KafkaListener(topics = "coffee-orders", groupId = "config-group")
    public void processCoffeeOrder(ConsumerRecord<String, byte[]> record) {
        try {
            CoffeeOrder coffeeOrder = decodeCoffeeOrder(record.value());
            log.info("Consumed Message, key: {}, value: {}", record.key(), coffeeOrder.toString());
        } catch (Exception e) {
            log.error("Exception is: {}", e.getMessage());
        }
    }

    public CoffeeOrder decodeCoffeeOrder(byte[] array) throws IOException {
        return CoffeeOrder.fromByteBuffer(ByteBuffer.wrap(array));
    }
}
