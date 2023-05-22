package com.knoldus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@ComponentScan
public class CoffeeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeOrderApplication.class, args);
    }
    }
