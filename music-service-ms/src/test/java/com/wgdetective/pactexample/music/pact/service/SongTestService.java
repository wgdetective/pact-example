package com.wgdetective.pactexample.music.pact.service;

import com.wgdetective.pactexample.music.dto.rest.SongDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class SongTestService {

    public static final String V_1_SONG_PATH = "/v1/songs/1";

    private final RestTemplate restTemplate;

    public SongDto getSong() {
        return restTemplate.getForObject(V_1_SONG_PATH, SongDto.class);
    }
}
