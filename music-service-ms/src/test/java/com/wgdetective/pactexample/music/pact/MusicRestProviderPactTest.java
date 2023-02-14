package com.wgdetective.pactexample.music.pact;

import java.util.List;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.wgdetective.pactexample.music.exception.SongNotFoundException;
import com.wgdetective.pactexample.music.model.Song;
import com.wgdetective.pactexample.music.repository.DBSongRepository;
import com.wgdetective.pactexample.music.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Provider("music-service-ms")
@Consumer("User")
@PactBroker(url = "http://localhost:9292")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicRestProviderPactTest {
    @LocalServerPort
    private int port;

    @MockBean
    private SongService songService;
    @MockBean
    private DBSongRepository songRepository;
    @MockBean
    private R2dbcScriptDatabaseInitializer r2dbcScriptDatabaseInitializer;

    @BeforeEach
    void setUp(final PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "music-service-ms"));
    }

    @Disabled
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyPact(final PactVerificationContext context) {
        context.getProviderInfo().setPackagesToScan(List.of("com.wgdetective.pactexample.music.pact"));
        context.verifyInteraction();
    }

    @State("song exists")
    void songDoesExist() {
        when(songService.findById(anyLong()))
                .thenReturn(Mono.just(new Song(1L, "Rick Astley", "Never Gonna Give You Up")));
    }

    @State("song does not exist")
    void songDoesNotExist() {
        when(songService.findById(anyLong()))
                .thenReturn(Mono.error(new SongNotFoundException()));
    }
}
