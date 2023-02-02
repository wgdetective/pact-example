package com.wgdetective.pactexample.music.dto.event;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AddSongEvent")
public record AddSongEvent(Long id, String author, String name) {

}
