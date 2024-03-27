FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

COPY bootstrap/pom.xml bootstrap/
COPY lib/pom.xml lib/
COPY locations/pom.xml locations/
COPY offers/pom.xml offers/
COPY score/pom.xml score/
COPY users/pom.xml users/

RUN mvn dependency:resolve-plugins

COPY bootstrap/src/main bootstrap/src/main
COPY lib/src/main lib/src/main
COPY locations/src/main locations/src/main
COPY offers/src/main offers/src/main
COPY score/src/main score/src/main
COPY users/src/main users/src/main

RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/bootstrap/target/bootstrap-0.0.1-SNAPSHOT.war .

EXPOSE 8081

CMD ["java", "-jar", "bootstrap-0.0.1-SNAPSHOT.war"]
