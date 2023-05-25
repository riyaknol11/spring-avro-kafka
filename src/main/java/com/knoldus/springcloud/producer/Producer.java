package com.knoldus.springcloud.producer;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class Producer {

    private KafkaTemplate<Integer, String> template;
    Faker faker;

    @EventListener(ApplicationStartedEvent.class)
    public void generate(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"pkc-xrnwx.asia-south2.gcp.confluent.cloud:9092" );
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='HSPK2HF3VAEJEW5A' password='W9LIBhHg1Z41xR97QxP/0C7XpZXw5oEFCRAEfYqR++BC4fy7MNbj9vg6iUFN2Xk9';");
        configProps.put("security.protocol","SASL_SSL");
        configProps.put("sasl.mechanism", "PLAIN");

        DefaultKafkaProducerFactory<Integer,String> pf = new DefaultKafkaProducerFactory<>(configProps); //NOSONAR
        template = new KafkaTemplate<>(pf); //NOSONAR
        faker = Faker.instance();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
        final Flux<String> quotes = Flux.fromStream(Stream.generate(()-> faker.hobbit().quote()));

        Flux.zip(interval, quotes).map(new Function<Tuple2<Long, String>, Object>() {
            @Override
            public Object apply(Tuple2<Long, String> iterator) {
                return template.send("hobbit", faker.random().nextInt(42), iterator.getT2());
            }
        }).blockLast();
    }

}
