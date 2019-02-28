FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ARG SERVICE_JAR_FILE=/target/urlreader-0.0.1-SNAPSHOT.jar
ADD ${SERVICE_JAR_FILE} urlreader.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/urlreader.jar"]