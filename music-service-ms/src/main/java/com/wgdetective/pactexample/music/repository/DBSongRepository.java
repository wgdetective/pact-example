package com.wgdetective.pactexample.music.repository;

import com.wgdetective.pactexample.music.entity.DBSongEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DBSongRepository extends ReactiveCrudRepository<DBSongEntity, Long> {

    Flux<DBSongEntity> findByAuthorAndName(String author, String name);
}
