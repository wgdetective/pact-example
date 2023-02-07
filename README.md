# Pact Example

## Architecture
[microservices.puml](docs%2Fmicroservices.puml)

## How to run
1) Set up Java 17 for project and for Gradle in IDE
2) Install docker
3) Run [docker-compose.yml](docker-compose.yml)

## Contract testing
1) Remove @Disabled from [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)
2) Run clean and build for [music-service-ms](music-service-ms)
3) Run pactPublish for [music-service-ms](music-service-ms)
4) Check http://localhost:9292/
5) Run clean and build for [music-grant-service-ms](music-grant-service-ms)
6) Pact should be verified in scope of [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)

## Knowing issues
1) ProducerPactTest stop working - reason is that there are some local changes that has broken version that depends on from gitHash(), just remove pact from pact-broker and make everything from the "Contract testing" instruction above
