package com.wgdetective.pactexample.music.util;

import com.wgdetective.pactexample.music.model.Song;

public class TestData {

    public static final String author = "Rick Astley";

    public static final String name = "Never Gonna Give You Up";

    private TestData() {
    }

    public static Song getSong() {
        return new Song(null, author, name);
    }
}
