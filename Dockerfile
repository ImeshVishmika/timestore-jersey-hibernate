# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY checkstyle.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/TimeStore-1.0-jar-with-dependencies.jar app.jar
COPY webapp ./webapp

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

