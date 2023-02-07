package com.wgdetective.pactexample.music;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.wgdetective.pactexample.music.entity.DBSongEntity;
import com.wgdetective.pactexample.music.repository.DBSongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("local")
public class MusicServiceUserStoriesTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private DBSongRepository songRepository;

    @Test
    void testGetNextSong() throws Exception {
        // given
        final var expectedJson = readResource("bdd/getNextSong.json");
        final var song = new DBSongEntity();
        song.setAuthor("Rick Astley");
        song.setName("Never Gonna Give You Up");
        songRepository.save(song).block();
        // when
        client.get().uri("/v1/recommendation/song")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedJson);

        // clean up
        songRepository.delete(song).block();
    }

    @Test
    void testGetNextSongSongNotFoundException() {
        // given
        // when
        client.get().uri("/v1/recommendation/song")
                .exchange()
                .expectStatus().isNotFound();
    }

    private String readResource(final String resourceName) throws IOException {
        try (final var resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
