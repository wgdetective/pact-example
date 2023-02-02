package com.wgdetective.pactexample.music.util;

import com.wgdetective.pactexample.music.model.Song;

public class TestData {

    public static final String author = "Михаил Елизаров";

    public static final String name = "Господин Главный Ветер";

    private TestData() {
    }

    public static Song getSong() {
        return new Song(null, author, name);
    }
}
