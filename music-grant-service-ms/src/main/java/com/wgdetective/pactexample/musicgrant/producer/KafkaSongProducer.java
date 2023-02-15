package com.wgdetective.pactexample.musicgrant.producer;

import com.wgdetective.pactexample.musicgrant.dto.event.AddSongEvent;
import com.wgdetective.pactexample.musicgrant.model.Song;
import com.wgdetective.pactexample.musicgrant.producer.mapper.SongEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log
@RequiredArgsConstructor
@Profile("kafka")
public class KafkaSongProducer implements SongProducer {

    private final ReactiveKafkaProducerTemplate<String, AddSongEvent> reactiveKafkaProducerTemplate;

    private final SongEventMapper mapper;

    @Value(value = "${musicgrant.kafka.song-topic}")
    private String topic;

    public void send(final Song song) {
        log.info(String.format("send to topic=%s, %s=%s,", topic, AddSongEvent.class.getSimpleName(), song));
        reactiveKafkaProducerTemplate.send(topic, mapper.map(song))
                .doOnSuccess(senderResult -> log.info(
                        String.format("sent %s offset : %s", song, senderResult.recordMetadata().offset())))
                .subscribe();
    }
}
