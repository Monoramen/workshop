FROM adoptopenjdk:17-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} olegborisof-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "olegborisof-0.0.1-SNAPSHOT.jar"]