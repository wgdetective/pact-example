package com.wgdetective.pactexample.musicgrant.util;

import com.wgdetective.pactexample.musicgrant.dto.rest.AddSongDto;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import com.wgdetective.pactexample.musicgrant.model.Song;

public class TestData {

    public static final Long id = 1L;
    public static final String author = "Rick Astley";

    public static final String name = "Never Gonna Give You Up";

    private TestData() {
    }

    public static Song getSong() {
        return new Song(null, author, name);
    }

    public static AddSongDto getAddSongDto() {
        return new AddSongDto(author, name);
    }

    public static SongDto getSongDto() {
        return new SongDto(id, author, name);
    }
}
