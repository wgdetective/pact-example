# Pact Example

## Architecture
[microservices.puml](docs%2Fmicroservices.puml)

## How to run
1) Set up Java 17 for project and for Gradle in IDE
2) Install docker
3) Run [docker-compose.yml](docker-compose.yml)

## How to test via REST
- run request to Docker from [add-song-example.http](music-grant-service-ms%2Fadd-song-example.http)
- wait for 10-30 sec
- run request to Docker from [get-recommended-song-example.http](music-service-ms%2Fget-recommended-song-example.http)

## Contract testing
1) Remove @Disabled from [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)
2) Uncomment pact part in [build.gradle.kts](music-service-ms%2Fbuild.gradle.kts)
3) Run clean and build for [music-service-ms](music-service-ms)
4) Run pactPublish for [music-service-ms](music-service-ms)
5) Check http://localhost:9292/
6) Run clean and build for [music-grant-service-ms](music-grant-service-ms)
7) Pact should be verified in scope of [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)

## Knowing issues
1) ProducerPactTest stop working - reason is that there are some local changes that has broken version that depends on from gitHash(), just remove pact from pact-broker and make everything from the "Contract testing" instruction above
