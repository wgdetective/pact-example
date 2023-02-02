package com.wgdetective.pactexample.music.controller;

import com.wgdetective.pactexample.music.controller.mapper.SongDtoMapper;
import com.wgdetective.pactexample.music.dto.rest.SongDto;
import com.wgdetective.pactexample.music.exception.SongNotFoundException;
import com.wgdetective.pactexample.music.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    private final SongDtoMapper songMapper;

    @GetMapping
    @RequestMapping("/song")
    public Mono<SongDto> recommendSong() {
        return recommendationService.recommendSong()
                .map(songMapper::map);
    }

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(SongNotFoundException.class)
    public void exceptionHandler() {
        //
    }
}
