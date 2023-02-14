package com.wgdetective.pactexample.musicgrant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.wgdetective.pactexample.musicgrant.producer.SongProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("local")
class MusicGrantServiceUserStoriesTests {

    @Autowired
    private WebTestClient client;

    @MockBean
    private SongProducer songProducer;

    @Test
    void testAddSong() throws Exception {
        // given
        final var rqJson = readResource("bdd/addSongRq.json");
        final var expectedJson = readResource("bdd/addSongRs.json");
        // when
        client.put().uri("/v1/song")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(rqJson)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expectedJson);
    }

    private String readResource(final String resourceName) throws IOException {
        try (final var resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
