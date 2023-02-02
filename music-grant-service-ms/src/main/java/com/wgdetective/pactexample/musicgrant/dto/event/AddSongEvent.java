package com.wgdetective.pactexample.musicgrant.dto.event;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("AddSongEvent")
public record AddSongEvent(String author, String name) {

}
