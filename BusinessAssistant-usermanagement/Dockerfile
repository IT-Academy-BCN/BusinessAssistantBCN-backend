FROM openjdk:17-alpine
ADD ["build/libs/BusinessAssistant-usermanagement-1.0-SNAPSHOT.jar", "/opt/BusinessAssistant-usermanagement/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-usermanagement/BusinessAssistant-usermanagement-1.0-SNAPSHOT.jar"]
EXPOSE 7778