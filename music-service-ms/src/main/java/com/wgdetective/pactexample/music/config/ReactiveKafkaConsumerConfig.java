package com.wgdetective.pactexample.music.config;

import java.util.Collections;

import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
@Profile("kafka")
public class ReactiveKafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, AddSongEvent> kafkaReceiverOptions(
            @Value(value = "${musicgrant.kafka.song-topic}") final String topic,
            final KafkaProperties kafkaProperties) {
        final ReceiverOptions<String, AddSongEvent> basicReceiverOptions = ReceiverOptions.create(
                kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, AddSongEvent> reactiveKafkaConsumerTemplate(
            final ReceiverOptions<String, AddSongEvent> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<>(kafkaReceiverOptions);
    }
}
