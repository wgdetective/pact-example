package com.wgdetective.pactexample.music.consumer.mapper;

import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import com.wgdetective.pactexample.music.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongEventMapper {

    Song map(final AddSongEvent song);
}
