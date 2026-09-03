# ------------------------------------------------------------
# Dockerfile for the Payment Processing System
#
# This Dockerfile creates a runnable image for the Spring Boot API.
# It starts from a Java 21 runtime image, copies the packaged JAR
# into the container, and runs the application on startup.
# ------------------------------------------------------------

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/payment-processing-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]