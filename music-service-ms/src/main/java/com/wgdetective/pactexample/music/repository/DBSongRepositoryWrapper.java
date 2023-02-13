package com.wgdetective.pactexample.music.repository;

import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.repository.mapper.DBSongEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
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
    public Flux<Song> findByAuthorAndName(final String author, final String name) {
        return dbSongRepository.findByAuthorAndName(author, name).map(mapper::map);
    }

    @Override
    public Mono<Song> save(final Song song) {
        return dbSongRepository.save(mapper.map(song)).map(mapper::map);
    }

    @Override
    public Mono<Long> count() {
        return dbSongRepository.count();
    }

    @Override
    public Mono<Song> findById(long id) {
        return dbSongRepository.findById(id).map(mapper::map);
    }
}
