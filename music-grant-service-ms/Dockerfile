FROM gradle:7.6-jdk17-alpine

WORKDIR /music-grant-service-ms
COPY . .
RUN gradle build

CMD gradle bootRun
