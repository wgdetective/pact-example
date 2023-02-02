package com.wgdetective.pactexample.musicgrant.service;

import com.wgdetective.pactexample.musicgrant.model.Song;
import com.wgdetective.pactexample.musicgrant.producer.SongProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongProducer songProducer;

    public Mono<Song> add(final Song song) {
        songProducer.send(song);
        return Mono.just(song);
    }
}
