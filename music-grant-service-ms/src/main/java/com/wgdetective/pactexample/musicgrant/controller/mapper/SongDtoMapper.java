package com.wgdetective.pactexample.musicgrant.controller.mapper;

import com.wgdetective.pactexample.musicgrant.dto.rest.AddSongDto;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import com.wgdetective.pactexample.musicgrant.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongDtoMapper {

    SongDto map(final Song song);

    @Mapping(target = "id", ignore = true)
    Song map(final AddSongDto song);
}
