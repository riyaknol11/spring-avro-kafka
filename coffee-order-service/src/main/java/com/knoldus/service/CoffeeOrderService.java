package com.knoldus.service;

import com.knoldus.config.KafkaConfig;
import com.knoldus.model.CoffeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CoffeeOrderService {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    @Autowired
    public CoffeeOrderService(KafkaTemplate<String, byte[]> kafkaTemplate, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    public void processOrder(CoffeeOrder coffeeOrder) throws IOException {
        byte[] value = coffeeOrder.toByteBuffer().array();

        kafkaTemplate.send(kafkaConfig.getCoffeeOrdersTopic(), value);
    }
}
