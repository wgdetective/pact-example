# Pact Example

## Architecture
[sequence.puml](docs%2Fsequence.puml)

## How to run
1) Set up Java 17 for project and for Gradle in IDE
2) Install docker
3) Run [docker-compose.yml](docker-compose.yml)

## How to test via REST
- run request to Docker from [add-song-example.http](music-grant-service-ms%2Fadd-song-example.http)
- wait for 10-30 sec
- run request to Docker from [get-recommended-song-example.http](music-service-ms%2Fget-recommended-song-example.http)

## Contract testing

### REST
1) Uncomment **pact** part in [build.gradle.kts](music-service-ms%2Fbuild.gradle.kts)
2) Run clean and build for [music-service-ms](music-service-ms), clean and install if maven is used
3) Run pactPublish for [music-service-ms](music-service-ms)
4) Check http://localhost:9292/ - User-music-service-ms pact should be there
5) Remove @Disabled
   from [MusicRestProviderPactTest.java](music-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusic%2Fpact%2FMusicRestProviderPactTest.java)
6) Run pact
   verification [MusicRestProviderPactTest.java](music-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusic%2Fpact%2FMusicRestProviderPactTest.java)

### Message Queue
1) Uncomment **pact** part in [build.gradle.kts](music-service-ms%2Fbuild.gradle.kts)
2) Run clean and build for [music-service-ms](music-service-ms), clean and install if maven is used
3) Run pactPublish for [music-service-ms](music-service-ms)
4) Check http://localhost:9292/ - music-service-ms-music-grant-service-ms pact should be there
5) Remove @Disabled
   from [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)
6) Run clean and build for [music-grant-service-ms](music-grant-service-ms), clean and install if maven is used
7) Pact should be verified in scope
   of [MusicGrantMQProducerPactTest.java](music-grant-service-ms%2Fsrc%2Ftest%2Fjava%2Fcom%2Fwgdetective%2Fpactexample%2Fmusicgrant%2Fpact%2FMusicGrantMQProducerPactTest.java)

## Copy pacts to resources
1) Run **copyPacts** task

## Knowing issues
1) ProducerPactTest stop working - reason is that there are some local changes that has broken version that depends on
   from gitHash(), just remove pact from pact-broker and make everything from the "Contract testing" instruction above
