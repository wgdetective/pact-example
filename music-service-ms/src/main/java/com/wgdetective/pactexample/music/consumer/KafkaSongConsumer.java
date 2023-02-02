package com.wgdetective.pactexample.music.consumer;

import com.wgdetective.pactexample.music.consumer.mapper.SongEventMapper;
import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.service.SongService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Log
@RequiredArgsConstructor
@Profile("kafka")
public class KafkaSongConsumer {

    private final ReactiveKafkaConsumerTemplate<String, AddSongEvent> reactiveKafkaConsumerTemplate;

    private final SongService service;

    private final SongEventMapper mapper;

    private Flux<Song> consumeSongs() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info(String.format("received key=%s, value=%s from topic=%s, offset=%s",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset()))
                )
                .map(ConsumerRecord::value)
                .map(mapper::map)
                .flatMap(service::add)
                .doOnError(
                        throwable -> log.severe("something bad happened while consuming : " + throwable.getMessage()));
    }

    @PostConstruct
    public void init() {
        // we have to trigger consumption
        consumeSongs().subscribe();
    }
}
