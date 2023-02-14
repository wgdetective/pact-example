package com.wgdetective.pactexample.musicgrant.pact;

import java.util.List;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.wgdetective.pactexample.musicgrant.exceptions.SongAlreadyExistsException;
import com.wgdetective.pactexample.musicgrant.model.Song;
import com.wgdetective.pactexample.musicgrant.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Provider("music-grant-service-ms")
@Consumer("User")
@PactBroker(url = "http://localhost:9292")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicGrantRestProviderPactTest {
    @LocalServerPort
    private int port;

    @MockBean
    private SongService songService;

    @BeforeEach
    void setUp(final PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "music-grant-service-ms"));
    }

    @Disabled
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(final PactVerificationContext context) {
        context.getProviderInfo().setPackagesToScan(List.of("com.wgdetective.pactexample.musicgrant.pact"));
        context.verifyInteraction();
    }

    @State("no songs exist")
    void addSong() {
        when(songService.add(any(Song.class)))
                .thenReturn(Mono.just(new Song(1L, "Rick Astley", "Never Gonna Give You Up")));
    }

    @State("song already exist")
    void addExistingSong() {
        when(songService.add(any(Song.class)))
                .thenReturn(Mono.error(new SongAlreadyExistsException()));
    }
}
