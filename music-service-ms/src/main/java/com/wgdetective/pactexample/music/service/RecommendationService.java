package com.wgdetective.pactexample.music.service;

import java.util.Random;

import com.wgdetective.pactexample.music.exception.SongNotFoundException;
import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final SongRepository songRepository;

    private final Random random = new Random();

    public Mono<Song> recommendSong() {
        return songRepository.count()
                .flatMap(size -> songRepository.findAll()
                        .switchIfEmpty(Flux.error(new SongNotFoundException()))
                        .skip(size > 0 ? random.nextInt(Math.toIntExact(size)) : 0)
                        .next());
    }
}
