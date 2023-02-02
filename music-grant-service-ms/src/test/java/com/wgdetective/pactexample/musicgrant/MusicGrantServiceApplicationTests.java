package com.wgdetective.pactexample.musicgrant;

import com.wgdetective.pactexample.musicgrant.producer.SongProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
class MusicGrantServiceApplicationTests {

    @MockBean
    private SongProducer songProducer;

    @Test
    void contextLoads() {
    }

}
