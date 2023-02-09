package com.wgdetective.pactexample.musicgrant.pact;

import java.util.HashMap;
import java.util.Map;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.wgdetective.pactexample.musicgrant.dto.rest.SongDto;
import com.wgdetective.pactexample.musicgrant.pact.service.AddSongTestService;
import com.wgdetective.pactexample.musicgrant.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@ActiveProfiles("pact-test")
class MusicGrantRestConsumerPactTest {

    @Pact(consumer = "User", provider = "music-grant-service-ms")
    RequestResponsePact addSong(final PactDslWithProvider builder) {
        return builder.uponReceiving("add song")
                .method("PUT")
                .path("/v1/song")
                .body("""
                        {
                            "author": "%s",
                            "name": "%s"
                        }""".formatted(TestData.author, TestData.name))
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body(LambdaDsl.newJsonBody(lambdaDslJsonBody -> {
                            lambdaDslJsonBody.numberType("id", TestData.id);
                            lambdaDslJsonBody.stringType("author", TestData.author);
                            lambdaDslJsonBody.stringType("name", TestData.name);
                        }).build()
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "addSong", pactVersion = PactSpecVersion.V3)
    void addSong(final MockServer mockServer) {
        final SongDto expected = TestData.getSongDto();

        final RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        final var songDto = new AddSongTestService(restTemplate).addSong(TestData.getAddSongDto());

        assertEquals(expected, songDto);
    }

    private Map<String, String> headers() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
