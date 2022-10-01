FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} domain-header.jar
ENTRYPOINT ["java", "-jar", "/domain-header.jar"]
EXPOSE 8081