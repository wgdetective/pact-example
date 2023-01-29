package com.wgdetective.pactexample.music.repository;

import com.wgdetective.pactexample.music.model.Song;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongRepository {

    Flux<Song> findAll();

    Mono<Long> count();
}
