FROM openjdk:17
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8087
CMD ["java", "-jar", "/app/app.jar"]
