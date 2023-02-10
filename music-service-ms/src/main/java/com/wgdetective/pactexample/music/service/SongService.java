package com.wgdetective.pactexample.music.service;

import com.wgdetective.pactexample.music.exception.SongNotFoundException;
import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public Mono<Song> findById(final long id) {
        return songRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new SongNotFoundException())));
    }

    public Mono<Song> add(final Song song) {
        return songRepository.findByAuthorAndName(song.author(), song.name())
                .next()
                .switchIfEmpty(Mono.just(song))
                .flatMap(song1 -> song1.id() == null ? songRepository.save(song1) : Mono.just(song1));
    }
}
