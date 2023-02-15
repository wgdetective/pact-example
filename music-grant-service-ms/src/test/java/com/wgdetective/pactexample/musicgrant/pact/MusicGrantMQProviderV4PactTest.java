package com.wgdetective.pactexample.musicgrant.pact;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.pactexample.musicgrant.dto.event.AddSongEvent;
import com.wgdetective.pactexample.musicgrant.model.Song;
import com.wgdetective.pactexample.musicgrant.producer.KafkaSongProducer;
import com.wgdetective.pactexample.musicgrant.producer.mapper.SongEventMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ActiveProfiles("pact-test")
@Provider("music-grant-service-ms")
@Consumer("music-service-ms-v4")
@PactBroker(url = "http://localhost:9292")
//@PactFolder("./pacts")
@ExtendWith(MockitoExtension.class)
class MusicGrantMQProviderV4PactTest {

    public static final String TOPIC = "topic";
    @Mock
    private ReactiveKafkaProducerTemplate<String, AddSongEvent> template;
    @Mock
    private SenderResult<Void> senderResult;
    private RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("a", 1), 1L, 1, 1L, 1, 1);

    @Captor
    private ArgumentCaptor<AddSongEvent> songEventCaptor;

    @Disabled
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(final Pact pact, final Interaction interaction, final PactVerificationContext context) {
        context.getProviderInfo().setPackagesToScan(List.of("com.wgdetective.pactexample.musicgrant.pact"));
        context.verifyInteraction();
    }

    @BeforeEach
    void before(final PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("valid song from kafka music-grant-service-ms (v4)")
    public String verifySongMessage() throws JsonProcessingException {
        SongEventMapper mapper = Mappers.getMapper(SongEventMapper.class);
        KafkaSongProducer kafkaSongProducer = new KafkaSongProducer(template, mapper, TOPIC);

        doReturn(recordMetadata).when(senderResult).recordMetadata();
        when(template.send(eq(TOPIC), songEventCaptor.capture())).thenReturn(Mono.just(senderResult));

        kafkaSongProducer.send(new Song(1L, "Rick Astley", "Never Gonna Give You Up"));

        AddSongEvent actual = songEventCaptor.getValue();

        return new ObjectMapper().writeValueAsString(actual);
    }
}
