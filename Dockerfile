FROM gradle:jdk11 AS builder
WORKDIR /tmp
COPY . /tmp
RUN /tmp/gradlew build


FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /tmp/build/libs/crawler-0.0.1-SNAPSHOT.jar  application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
