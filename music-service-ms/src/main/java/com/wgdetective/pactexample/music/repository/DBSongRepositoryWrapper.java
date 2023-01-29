package com.wgdetective.pactexample.music.repository;

import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.repository.mapper.DBSongEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DBSongRepositoryWrapper implements SongRepository {

    private final DBSongRepository dbSongRepository;

    private final DBSongEntityMapper mapper;

    @Override
    public Flux<Song> findAll() {
        return dbSongRepository.findAll().map(mapper::map);
    }

    @Override
    public Mono<Long> count() {
        return dbSongRepository.count();
    }
}
