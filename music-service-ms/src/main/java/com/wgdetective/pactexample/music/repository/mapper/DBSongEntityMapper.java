package com.wgdetective.pactexample.music.repository.mapper;

import com.wgdetective.pactexample.music.entity.DBSongEntity;
import com.wgdetective.pactexample.music.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DBSongEntityMapper {

    Song map(final DBSongEntity song);

    @Mapping(target = "version", ignore = true)
    DBSongEntity map(final Song song);
}
