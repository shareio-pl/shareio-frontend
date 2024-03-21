
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

COPY lib/src lib/src
COPY lib/pom.xml lib/

COPY bootstrap/src bootstrap/src
COPY bootstrap/pom.xml bootstrap/

COPY offers/src offers/src
COPY offers/pom.xml offers/

COPY score/src score/src
COPY score/pom.xml score/

COPY users/src users/src
COPY users/pom.xml users/

RUN mvn dependency:resolve-plugins
RUN mvn clean package -DskipTests

RUN jar tf /app/bootstrap/target/bootstrap-0.0.1-SNAPSHOT.war

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/bootstrap/target/bootstrap-0.0.1-SNAPSHOT.war .

EXPOSE 8082

CMD ["java", "-jar", "bootstrap-0.0.1-SNAPSHOT.war"]





















## Build
#FROM maven:3.9-eclipse-temurin-21-alpine AS dependencies
#WORKDIR /app
#COPY pom.xml .
#COPY bootstrap/pom.xml bootstrap/pom.xml
#COPY lib/pom.xml lib/pom.xml
#COPY offers/pom.xml offers/pom.xml
#COPY score/pom.xml score/pom.xml
#COPY users/pom.xml users/pom.xml
#RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.6.1:go-offline
#
#FROM maven:3.9-eclipse-temurin-21-alpine AS build
#WORKDIR /app
#COPY --from=dependencies /root/.m2 /root/.m2
#COPY --from=dependencies /app /app
#COPY bootstrap/src bootstrap/src
#COPY lib/src lib/src
#COPY offers/src offers/src
#COPY score/src score/src
#COPY users/src users/src
#RUN mvn -B -e -o clean install

#FROM maven:3.9-eclipse-temurin-21-alpine AS build
#WORKDIR /app
#COPY .mvn .mvn
#COPY pom.xml .
#COPY bootstrap bootstrap
#COPY lib lib
#COPY offers offers
#COPY score score
#COPY users users
#RUN mvn -B -e clean install
#
## Run
#FROM eclipse-temurin:21-jre-alpine
#COPY --from=build /app/bootstrap/target/bootstrap-0.0.1-SNAPSHOT.jar .
#EXPOSE 8081
#CMD ["java","-jar","backend-0.0.1-SNAPSHOT.jar"]