package com.wgdetective.pactexample.musicgrant.pact.service;

import com.wgdetective.pactexample.musicgrant.dto.rest.AddSongDto;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class AddSongTestService {

    public static final String V_1_SONG_PATH = "/v1/song";

    private final RestTemplate restTemplate;

    public SongDto addSong(final AddSongDto addSongDto) {
        return restTemplate.exchange(V_1_SONG_PATH,
                        HttpMethod.PUT,
                        RequestEntity.put("/v1/song")
                                .body(addSongDto, AddSongDto.class), SongDto.class)
                .getBody();
    }
}
