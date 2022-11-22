FROM bellsoft/liberica-openjdk-alpine:17.0.1
RUN apk add -U consul
ADD ["build/libs/BusinessAssistant-login-1.0-BETA.jar", "/opt/BusinessAssistant-login/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-login/BusinessAssistant-login-1.0-BETA.jar"]
EXPOSE 7779