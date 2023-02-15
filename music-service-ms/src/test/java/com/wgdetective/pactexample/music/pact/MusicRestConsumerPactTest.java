package com.wgdetective.pactexample.music.pact;

import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wgdetective.pactexample.music.dto.rest.SongDto;
import com.wgdetective.pactexample.music.pact.service.SongTestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@ActiveProfiles("pact-test")
class MusicRestConsumerPactTest {

    @Pact(consumer = "User", provider = "music-service-ms")
    V4Pact getNonExistingSong(final PactBuilder builder) {
        return builder.given("song does not exist")
                .expectsToReceiveHttpInteraction("get song", http -> http
                        .withRequest(request -> request.method("GET")
                                .path("/v1/songs/1"))
                        .willRespondWith(response ->
                                response.status(404)))
                .toPact();
    }


    @Pact(consumer = "User", provider = "music-service-ms")
    V4Pact getExistingSong(final PactBuilder builder) {
        PactDslJsonBody responseBody = new PactDslJsonBody()
                .numberValue("id", 1)
                .stringValue("author", "Rick Astley")
                .stringValue("name", "Never Gonna Give You Up");

        return builder.given("song exists")
                .expectsToReceiveHttpInteraction("get song", http -> http
                        .withRequest(request -> request.method("GET")
                                .path("/v1/songs/1"))
                        .willRespondWith(response ->
                                response.status(200)
                                        .headers(headers())
                                        .body(responseBody)))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getNonExistingSong", pactVersion = PactSpecVersion.V4)
    void getNonExistingSong(final MockServer mockServer) {

        final RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        final var service = new SongTestService(restTemplate);
        assertThrows(HttpClientErrorException.class, service::getSong);
    }

    @Test
    @PactTestFor(pactMethod = "getExistingSong", pactVersion = PactSpecVersion.V4)
    void getExistingSong(final MockServer mockServer) {
        final SongDto expected = new SongDto(1L, "Rick Astley", "Never Gonna Give You Up");

        final RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        final var songDto = new SongTestService(restTemplate).getSong();

        assertEquals(expected, songDto);
    }

    private Map<String, String> headers() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
