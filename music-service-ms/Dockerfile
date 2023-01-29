FROM gradle:7.6-jdk17-alpine

WORKDIR /music-service-ms
COPY . .
RUN gradle clean build

CMD gradle bootRun
