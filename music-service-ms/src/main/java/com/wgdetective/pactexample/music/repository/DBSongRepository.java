package com.wgdetective.pactexample.music.repository;

import java.util.UUID;

import com.wgdetective.pactexample.music.entity.DBSongEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBSongRepository extends ReactiveCrudRepository<DBSongEntity, UUID> {

}
