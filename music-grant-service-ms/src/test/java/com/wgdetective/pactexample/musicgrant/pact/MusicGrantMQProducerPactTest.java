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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@Provider("music-grant-service-ms")
@Consumer("music-service-ms")
@PactBroker(url = "http://localhost:9292")
//@PactFolder("./pacts")
public class MusicGrantMQProducerPactTest {

    @Disabled
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(final Pact pact, final Interaction interaction, final PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(final PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("valid song from kafka music-grant-service-ms")
    public String verifySongMessage() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(new AddSongEvent("Михаил Елизаров", "Господин Главный Ветер"));
    }
}
