package com.wgdetective.pactexample.music.controller.mapper;

import com.wgdetective.pactexample.music.dto.SongDto;
import com.wgdetective.pactexample.music.model.Song;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongDtoMapper {

    SongDto map(final Song song);
}
