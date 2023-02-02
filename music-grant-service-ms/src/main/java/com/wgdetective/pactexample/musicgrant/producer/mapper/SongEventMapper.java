package com.wgdetective.pactexample.musicgrant.producer.mapper;

import com.wgdetective.pactexample.musicgrant.dto.event.AddSongEvent;
import com.wgdetective.pactexample.musicgrant.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongEventMapper {

    AddSongEvent map(final Song song);
}
