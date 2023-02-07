package com.wgdetective.pactexample.music.pact;

import java.util.List;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "music-grant-service-ms", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V3)
public class MusicMQConsumerPactTest {

    @Pact(consumer = "music-service-ms", provider = "music-grant-service-ms")
    public MessagePact validMessageFromKafkaProvider(final MessagePactBuilder builder) {
        return builder
                .expectsToReceive("valid song from kafka music-grant-service-ms")
                .withContent(LambdaDsl.newJsonBody(lambdaDslJsonBody -> {
                    lambdaDslJsonBody.stringType("author");
                    lambdaDslJsonBody.stringType("name");
                }).build())
                .toPact();
    }

    @SneakyThrows
    @Test
    @PactTestFor(pactMethod = "validMessageFromKafkaProvider")
    public void testValidDateFromProvider(final List<Message> messages) {
        assertThat(messages).isNotEmpty();
        assertThat(new ObjectMapper().readValue(new String(messages.get(0).contentsAsBytes()), AddSongEvent.class))
                .hasFieldOrProperty("author")
                .hasFieldOrProperty("name");
    }
}
