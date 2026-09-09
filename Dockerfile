FROM maven:3.9-eclipse-temurin-22 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=build /app/flynow-application/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]