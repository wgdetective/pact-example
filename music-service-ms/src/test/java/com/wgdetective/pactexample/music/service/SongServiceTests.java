package com.wgdetective.pactexample.music.service;

import com.wgdetective.pactexample.music.util.TestData;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongServiceTests {

    @Autowired
    private SongService service;

    @Autowired
    private RecommendationService recommendationService;

    @Test
    @Order(1)
    void testAddNewSong() {
        // given
        final var song = TestData.getSong();
        // when
        final var savedSong = service.add(song).block();
        // then
        assertNotNull(savedSong);
        assertNotNull(savedSong.id());
    }

    @Test
    @Order(2)
    void testAddSongWhenExisting() {
        // given
        final var preSavedSong = recommendationService.recommendSong().block();
        final var song = TestData.getSong();
        // when
        final var savedSong = service.add(song).block();
        // then
        assertNotNull(preSavedSong);
        assertNotNull(preSavedSong.id());
        assertNotNull(savedSong);
        assertNotNull(savedSong.id());
        assertEquals(preSavedSong.id(), savedSong.id());
    }
}
