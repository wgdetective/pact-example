package com.wgdetective.pactexample.music.controller;

import com.wgdetective.pactexample.music.controller.mapper.SongDtoMapper;
import com.wgdetective.pactexample.music.dto.rest.SongDto;
import com.wgdetective.pactexample.music.exception.SongNotFoundException;
import com.wgdetective.pactexample.music.service.RecommendationService;
import com.wgdetective.pactexample.music.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    private final SongDtoMapper songMapper;

    @GetMapping
    @RequestMapping("/{id}")
    public Mono<SongDto> recommendSong(@PathVariable long id) {
        return songService.findById(id)
                .map(songMapper::map);
    }


    @ResponseStatus(
            value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(SongNotFoundException.class)
    public void exceptionHandler() {
        //
    }
}
