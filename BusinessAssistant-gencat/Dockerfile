FROM openjdk:17-alpine
ADD ["build/libs/BusinessAssistant-gencat-1.0-SNAPSHOT.jar", "/opt/BusinessAssistant-gencat/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-gencat/BusinessAssistant-gencat-1.0-SNAPSHOT.jar"]
EXPOSE 7777