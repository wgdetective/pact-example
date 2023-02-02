package com.wgdetective.pactexample.music.repository;

import com.wgdetective.pactexample.music.model.Song;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SongRepository {

    Flux<Song> findAll();

    Flux<Song> findByAuthorAndName(final String author, final String name);

    Mono<Song> save(final Song song);

    Mono<Long> count();
}
