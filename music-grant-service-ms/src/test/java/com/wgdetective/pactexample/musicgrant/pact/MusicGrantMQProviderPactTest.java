package com.wgdetective.pactexample.musicgrant.pact;

import java.util.List;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("pact-test")
@Provider("music-grant-service-ms")
@Consumer("music-service-ms")
@PactBroker(url = "http://localhost:9292")
//@PactFolder("./pacts")
class MusicGrantMQProviderPactTest {

    @Disabled("Disabled to allow make build when pact hasn't yet been published")
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

    @PactVerifyProvider("valid song from kafka music-grant-service-ms")
    public String verifySongMessage() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new AddSongEvent("Rick Astley", "Never Gonna Give You Up"));
    }
}
