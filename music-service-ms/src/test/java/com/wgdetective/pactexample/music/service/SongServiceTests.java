package com.wgdetective.pactexample.music.service;

import com.wgdetective.pactexample.music.repository.DBSongRepository;
import com.wgdetective.pactexample.music.util.TestData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SongServiceTests {

    @Autowired
    private SongService service;
    @Autowired
    private DBSongRepository songRepository;

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

    @AfterAll
    void cleanUp() {
        songRepository.deleteAll().block();
    }
}
