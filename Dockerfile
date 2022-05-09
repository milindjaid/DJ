FROM openjdk:8-jre-alpine
COPY target/Spring-Hibernate-Intehration-0.0.1-SNAPSHOT.jar  Spring-Hibernate-Intehration-0.0.1-SNAPSHOT.jar
CMD java -jar -Dspring.profiles.active=prod Spring-Hibernate-Intehration-0.0.1-SNAPSHOT.jar