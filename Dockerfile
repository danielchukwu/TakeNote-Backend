FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/TakeNoteBackend-0.0.1-SNAPSHOT.jar TakeNoteBackend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/TakeNoteBackend-0.0.1-SNAPSHOT.jar"]