package com.wgdetective.pactexample.music.pact;

import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.pactexample.music.dto.event.AddSongEvent;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "music-grant-service-ms", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
class MusicMQConsumerV4PactTest {

    @Pact(consumer = "music-service-ms-v4", provider = "music-grant-service-ms")
    public V4Pact validMessageFromKafkaProvider(final PactBuilder builder) {
        return builder.given("any")
                .usingLegacyMessageDsl()
                .expectsToReceive("valid song from kafka music-grant-service-ms (v4)")
                .withContent(LambdaDsl.newJsonBody(lambdaDslJsonBody -> {
                    lambdaDslJsonBody.stringType("author");
                    lambdaDslJsonBody.stringType("name");
                }).build())
                .toPact();
    }

    @SneakyThrows
    @Test
    @PactTestFor(pactMethod = "validMessageFromKafkaProvider")
    void testValidDateFromProvider(final List<V4Interaction.AsynchronousMessage> messages) {
        assertThat(messages).isNotEmpty();
        byte[] messageBody = messages.get(0).asAsynchronousMessage().getContents()
                .getContents().getValue();
        assertThat(new ObjectMapper().readValue(new String(messageBody), AddSongEvent.class))
                .hasFieldOrProperty("author")
                .hasFieldOrProperty("name");
    }
}
