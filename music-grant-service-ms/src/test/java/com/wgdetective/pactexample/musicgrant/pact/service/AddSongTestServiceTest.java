package com.wgdetective.pactexample.musicgrant.pact.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import com.wgdetective.pactexample.musicgrant.util.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.cloud.contract.wiremock.WireMockSpring.options;

@ActiveProfiles("pact-test")
class AddSongTestServiceTest {

    private WireMockServer wireMockServer;

    private AddSongTestService addSongTestService;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();

        final RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(wireMockServer.baseUrl())
                .build();

        addSongTestService = new AddSongTestService(restTemplate);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getAllProducts() {
        wireMockServer.stubFor(put(urlPathEqualTo(AddSongTestService.V_1_SONG_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n"
                                + "  \"id\": " + TestData.id + ",\n"
                                + "  \"author\": \"" + TestData.author + "\",\n"
                                + "  \"name\": \"" + TestData.name + "\"\n"
                                + "}\n")));

        final SongDto expected = TestData.getSongDto();

        final var songDto = addSongTestService.addSong(TestData.getAddSongDto());

        assertEquals(expected, songDto);
    }
}
