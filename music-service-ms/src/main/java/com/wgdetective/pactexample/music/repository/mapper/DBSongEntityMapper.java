package com.wgdetective.pactexample.music.repository.mapper;

import com.wgdetective.pactexample.music.entity.DBSongEntity;
import com.wgdetective.pactexample.music.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DBSongEntityMapper {

    Song map(final DBSongEntity song);
}
