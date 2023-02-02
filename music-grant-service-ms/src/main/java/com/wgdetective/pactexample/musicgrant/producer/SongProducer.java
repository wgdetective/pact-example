package com.wgdetective.pactexample.musicgrant.producer;

import com.wgdetective.pactexample.musicgrant.model.Song;

public interface SongProducer {

    void send(Song song);
}
