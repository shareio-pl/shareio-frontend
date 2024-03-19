# Build
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
# TODO: fix (submodules don't build)
RUN mvn package

# Run
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
CMD ["java","-jar","backend-0.0.1-SNAPSHOT.jar"]