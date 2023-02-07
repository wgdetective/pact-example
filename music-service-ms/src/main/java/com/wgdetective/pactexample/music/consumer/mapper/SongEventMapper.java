package com.wgdetective.pactexample.music.consumer.mapper;

import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import com.wgdetective.pactexample.music.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongEventMapper {

    @Mapping(target = "id", ignore = true)
    Song map(final AddSongEvent song);
}
