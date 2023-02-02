package com.wgdetective.pactexample.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class MusicServiceApplication {

    public static void main(final String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(MusicServiceApplication.class, args);
    }

}
