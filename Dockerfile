FROM gradle:8.7-jdk21 AS builder

WORKDIR /app

COPY . .

RUN gradle shadowJar

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*all.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]