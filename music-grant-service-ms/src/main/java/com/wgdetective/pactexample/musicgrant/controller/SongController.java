package com.wgdetective.pactexample.musicgrant.controller;

import com.wgdetective.pactexample.musicgrant.controller.mapper.SongDtoMapper;
import com.wgdetective.pactexample.musicgrant.dto.rest.AddSongDto;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import com.wgdetective.pactexample.musicgrant.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/song")
@RequiredArgsConstructor
public class SongController {

    private final SongDtoMapper mapper;

    private final SongService service;

    @PutMapping
    public Mono<SongDto> add(@RequestBody final AddSongDto songRq) {
        return service.add(mapper.map(songRq)).map(mapper::map);
    }
}
