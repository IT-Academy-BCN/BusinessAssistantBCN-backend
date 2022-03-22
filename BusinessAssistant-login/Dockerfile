FROM openjdk:17-alpine

ADD ["build/libs/BusinessAssistant-login-1.0-SNAPSHOT.jar", "/opt/businessassistant-login/"]

ENTRYPOINT ["java", "-jar", "/opt/businessassistant-login/BusinessAssistant-login-1.0-SNAPSHOT.jar"]

EXPOSE 7777